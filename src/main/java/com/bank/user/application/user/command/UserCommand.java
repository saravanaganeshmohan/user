package com.bank.user.application.user.command;

import com.bank.user.application.domain.Status;
import com.bank.user.application.domain.UserDocument;
import com.bank.user.application.domain.Users;
import com.bank.user.application.exception.UserException;
import com.bank.user.application.openfeign.ThirdPartyAPI;
import com.bank.user.application.user.model.CommentResponse;
import com.bank.user.application.user.model.ErrorResponse;
import com.bank.user.application.user.model.PostResponse;
import com.bank.user.application.user.model.UserDocumentResponse;
import com.bank.user.application.user.request.DeleteRequest;
import com.bank.user.application.user.request.PostCommentRequest;
import com.bank.user.application.user.request.PostRequest;
import com.bank.user.application.user.request.UserRequest;
import com.bank.user.repository.persistence.UserDocumentRepository;
import com.bank.user.repository.persistence.UserRepository;
import com.bank.user.util.CommonUtil;
import com.bank.user.util.Constant;
import com.bank.user.util.JsonConverter;
import com.bank.user.util.AESUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCommand {

    private static Logger log = LoggerFactory.getLogger(UserCommand.class);

    @Value("${media.storage.path}")
    private String mediaStoragePath;

    @Value("${media.storage.deletePath}")
    private String mediaDeletePath;

    @Value("${media.storage.viewPath}")
    private String viewMediaStoragePath;

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDocumentRepository userDocumentRepository;

    @Autowired
    ThirdPartyAPI thirdPartyAPI;

    @Autowired
    AESUtil aesUtil;

    /**
     * Method that save the user document
      *@param userRequest
     * @param userId
     * @return List<UserDocument>
     */
    @Transactional
    public  List<UserDocument> saveUserDocument(UserRequest userRequest,Long userId) {
        try {
            log.info("Inside saveUserDocument command start");
            Optional <Users> usersOptional = userRepository.findById(userId);
            if(usersOptional.isPresent()){
                List<UserDocument> userDocuments = new ArrayList<>();
                for (MultipartFile file : userRequest.getFiles()) {
                    Optional<UserDocument> userDocumentOptional = userDocumentRepository.findByUsers_UserIdAndDocumentName(userId, file.getOriginalFilename());
                    UUID uuid = UUID.randomUUID();
                    if(userDocumentOptional.isPresent()){
                        uuid =userDocumentOptional.get().getUserDocumentId();
                    }
                    userDocuments.add(handleSaveDocument(file, usersOptional.get(),uuid));
                }
                log.info("Inside saveUserDocument command end");
                return userDocumentRepository.saveAll(userDocuments);
            } else {
                throw new UserException(new ErrorResponse("User id not found"));
            }
        } catch (Exception e) {
            throw new UserException(new ErrorResponse("Save Document Internal server error"));
        }
    }

    /**
     * Method that get list of the user documents
     * @param userId
     * @return List<UserDocumentResponse>
     */
    public List<UserDocumentResponse> getUserDocument(Long userId) {
        try {
            log.info("Inside getUserDocument command start");
            List<UserDocumentResponse> userDocumentResponses = new ArrayList<>();
            List<UserDocument> userDocuments = userDocumentRepository.findByUsers_UserIdAndStatus(userId, new Status(Constant.ACTIVE_STATUS_ID));
            userDocuments.stream().forEach(userDocument -> {
                decryptedFile(userDocument.getDocumentLocation());
                String absolutePath = CommonUtil.PDFPasswordProtect(viewMediaStoragePath+ userDocument.getDocumentLocation(),CommonUtil.getPDFPassword(userDocument.getUsers().getName(),userDocument.getUsers().getDob()));
                userDocument.setDocumentLocation(absolutePath);
                userDocumentResponses.add(JsonConverter.fromUserDocumentResponse(userDocument));
            });
            return userDocumentResponses;
        } catch (Exception e) {
            throw new UserException(new ErrorResponse("Internal server error"));
        }
    }

    /**
     * Method that update user documents
     * @param userRequest
     * @param userId
     * @return String
     */
    @Transactional
    public String updateUserDocument(UserRequest userRequest,Long userId) {
        try {
            log.info("Inside updateUserDocument command start");
            List<UserDocument> userDocuments = new ArrayList<>();
            for (MultipartFile file : userRequest.getFiles()) {
                Optional<UserDocument> userDocumentOptional = userDocumentRepository.findByUsers_UserIdAndDocumentName(userId, file.getOriginalFilename());
                if (userDocumentOptional.isPresent()) {
                    UserDocument userDocument = userDocumentOptional.get();
                    handleFileUpload(userDocument.getUserDocumentId(), file);
                    userDocuments.add(userDocument);
                } else {
                    Optional<Users> users = userRepository.findById(userId);
                    if(users.isPresent()){
                        userDocuments.add(handleSaveDocument(file, users.get(),UUID.randomUUID()));
                    }
                }
            }
            userDocumentRepository.saveAll(userDocuments);
            log.info("Inside updateUserDocument command end");
            return "User document updated successfully";
        } catch (Exception e) {
            throw new UserException(new ErrorResponse("update UserDocument Internal server error"));
        }
    }

    /**
     * Method that delete user document
     * @param request
     * @param userId
     * @return String
     */
    @Transactional
    public String deleteUserDocument(DeleteRequest request,Long userId) {
        try {
            log.info("Inside deleteUserDocument command start");
            Optional<UserDocument> userDocument = userDocumentRepository.findByUsers_UserIdAndUserDocumentId(userId,request.getUserDocumentId());
            if (userDocument.isPresent()) {
                userDocument.get().setStatus(new Status(Constant.DELETED_STATUS_ID));
                fileTransfer(userDocument.get().getDocumentLocation());
                userDocumentRepository.save(userDocument.get());
            }else{
                return "User Document Id Not Found";
            }
            log.info("Inside deleteUserDocument command end");
            return "User document deleted successfully";
        } catch (Exception e) {
            throw new UserException(new ErrorResponse("delete UserDocument Internal server error"));
        }
    }

    /**
     * Method that create post
     * @param userId
     * @param postRequest
     * @return PostResponse
     */
    public PostResponse createPost(Long userId,PostRequest postRequest) {
        try {
            log.info("Inside createPost command start");
            postRequest.setUserId(userId);
            PostResponse postResponse = thirdPartyAPI.createPost(postRequest);
            log.info("Inside createPost command end");
            return postResponse;
        }catch (Exception e){
            throw new UserException(new ErrorResponse("create Post Internal server error"));
        }
    }

    /**
     * Method that create comment
     * @param userId
     * @param postCommentRequest
     * @return PostResponse
     */
    public CommentResponse createComment(Long userId, PostCommentRequest postCommentRequest) {
        try {
            log.info("Inside createComment command start");
            postCommentRequest.setUserId(userId);
            return thirdPartyAPI.createComment(postCommentRequest);
        } catch (Exception e) {
            throw new UserException(new ErrorResponse("create Comment Internal server error"));
        }
    }

    /**
     * Method that get post
     * @param userId
     * @return Object
     */
    public Object getPosts(Long userId) {
        try {
            log.info("Inside getPosts command");
            return thirdPartyAPI.getPosts(userId);
        } catch (Exception e) {
            throw new UserException(new ErrorResponse("get post Internal server error"));
        }
    }

    private UserDocument handleSaveDocument(MultipartFile file, Users users,UUID uuid) throws IOException {
        try {
            log.info("Inside handleSaveDocument");
            UserDocument userDocument = new UserDocument();
            userDocument.setUsers(users);
            userDocument.setDocumentName(file.getOriginalFilename());
            userDocument.setStatus(new Status(Constant.ACTIVE_STATUS_ID));
            userDocument.setUserDocumentId(uuid);
            userDocument.setDocumentLocation(handleFileUpload(uuid, file));
            return userDocument;
        } catch (Exception e) {
            throw e;
        }
    }

    public String handleFileUpload(UUID uuid, MultipartFile file) throws IOException {
        String filename = null;
        try {
            filename = uuid + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            if (!FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("pdf")) {
                throw new UserException(new ErrorResponse("Kindly upload the pdf file"));
            }
            File encryptedFile = new File(mediaStoragePath + filename);
            aesUtil.doCrypto(Cipher.ENCRYPT_MODE, secretKey, file.getBytes(), encryptedFile);
        } catch (Exception e) {
            throw e;
        }
        return filename;
    }

    private void decryptedFile(String filePath){
        try {
            File file = new File(mediaStoragePath +filePath);
            File decryptedFileOutputPath = new File(viewMediaStoragePath +filePath);
            aesUtil.doCrypto(Cipher.DECRYPT_MODE, secretKey, Files.readAllBytes(file.toPath()) , decryptedFileOutputPath);
        }catch (Exception e){
            throw new UserException(new ErrorResponse("Internal server error"));
        }

    }

    private void fileTransfer(String documentLocation) {
        try {
            Files.move(Paths.get(mediaStoragePath + documentLocation), Paths.get(mediaDeletePath + documentLocation));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
