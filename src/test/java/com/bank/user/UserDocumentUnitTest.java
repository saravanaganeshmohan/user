package com.bank.user;


import com.bank.user.application.domain.Status;
import com.bank.user.application.domain.UserDocument;
import com.bank.user.application.domain.Users;
import com.bank.user.repository.persistence.StatusRepository;
import com.bank.user.repository.persistence.UserDocumentRepository;
import com.bank.user.repository.persistence.UserRepository;
import com.bank.user.util.Constant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserDocumentUnitTest {
    @Autowired
    private UserDocumentRepository userDocumentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StatusRepository statusRepository;
    UserDocument userDocument;
    Users users;
    Status status;

    @BeforeEach
    void setup(){
        status = createStatus();
        users = createUser(status);
        userDocument = createUserDocument(status,users);
    }

    @AfterEach
    void deleteDocumentData(){
        userDocument = null;
        userDocumentRepository.deleteAll();
    }

    @Test
    void userDocumentSuccess(){
        Optional<UserDocument> userDocumentOptional = userDocumentRepository.findByUsers_UserIdAndDocumentName(1L,"loanchart_May_25_2023_13_02_24 (1).pdf");
        if(userDocumentOptional.isPresent()){
            UserDocument userDocumentData = userDocumentOptional.get();
            assertThat(userDocumentData.getUserDocumentId()).isEqualTo(userDocument.getUserDocumentId());
        }
    }
    @Test
    void deleteDocument(){
        Optional<UserDocument> userDocumentOptional = userDocumentRepository.findByUsers_UserIdAndDocumentName(1L,"loanchart_May_25_2023_13_02_24 (1).pdf");
        if(userDocumentOptional.isPresent()){
            UserDocument userDocumentData = userDocumentOptional.get();
            userDocumentData.setStatus(new Status(3L));
            userDocumentData = userDocumentRepository.save(userDocumentData);
            assertThat(userDocumentData.getUserDocumentId()).isEqualTo(userDocument.getUserDocumentId());
            assertThat(userDocumentData.getStatus().getStatusId()).isEqualTo(Constant.DELETED_STATUS_ID);
        }
    }

    private Status createStatus(){
        Status status = new Status();
        status.setStatusId(1L);
        status.setName("ACTIVE");
        return statusRepository.save(status);
    }

    private Users createUser(Status status){
        Users users = new Users();
        users.setUserId(1L);
        users.setName("kumar k");
        users.setFirstName("kumar");
        users.setLastName("k");
        users.setDob("1990-01-01");
        users.setStatus(status);
        return userRepository.save(users);
    }

    private UserDocument createUserDocument(Status status, Users users) {
            UserDocument document = new UserDocument();
            UUID uuid = UUID.fromString("57de8ff0-f58d-48e6-8940-4dd029fe6e0f");
            document.setUserDocumentId(uuid);
            document.setDocumentName("loanchart_May_25_2023_13_02_24 (1).pdf");
            document.setDocumentLocation("57de8ff0-f58d-48e6-8940-4dd029fe6e0f.pdf");
            document.setUsers(users);
            document.setStatus(status);
            return userDocumentRepository.save(document);
    }
}
