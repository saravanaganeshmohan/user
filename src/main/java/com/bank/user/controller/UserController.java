package com.bank.user.controller;

import com.bank.user.application.user.command.UserCommand;
import com.bank.user.application.user.model.Response;
import com.bank.user.application.user.request.DeleteRequest;
import com.bank.user.application.user.request.PostCommentRequest;
import com.bank.user.application.user.request.PostRequest;
import com.bank.user.application.user.request.UserRequest;
import com.bank.user.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends ControllerException {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserCommand userDocumentCommand;

    @PostMapping(value = "/user/{userId}/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<Response> uploadUserDocument(@ModelAttribute UserRequest userRequest, @PathVariable Long userId) {
        log.info("Inside uploadUserDocument controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.saveUserDocument(userRequest,userId)));
        }catch (Exception exception) {
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
        }
    }
    @GetMapping(value = "/user/{userId}/document")
    public ResponseEntity<Response> getUserDocument(@PathVariable Long userId){
        log.info("Inside getUserDocument controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.getUserDocument(userId)));
        }catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
        }
    }
    @PatchMapping(value = "/user/{userId}/document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response> updateUserDocument(@ModelAttribute UserRequest userRequest, @PathVariable Long userId){
        log.info("Inside updateUserDocument controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.updateUserDocument(userRequest,userId)));
        }catch (Exception exception){
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
        }
    }

    @DeleteMapping(value = "/user/{userId}/document")
    public ResponseEntity<Response> deleteUserDocument(@RequestBody DeleteRequest requests, @PathVariable Long userId){
        log.info("Inside deleteUserDocument controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.deleteUserDocument(requests,userId)));
        }catch (Exception exception){
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
        }
    }

    @PostMapping(value = "/user/{userId}/document/post",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createPost(@RequestBody PostRequest postRequest, @PathVariable Long userId){
        log.info("Inside createPost controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.createPost(userId,postRequest)));
        }catch (Exception exception){
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
        }
    }

    @PostMapping(value = "/user/{userId}/document/post/Comment",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> createComment(@RequestBody PostCommentRequest postCommentRequest,@PathVariable Long userId){
        log.info("Inside createComment controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.createComment(userId,postCommentRequest)));
        }catch (Exception exception){
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()));
        }
    }

    @GetMapping(value = "/user/{userId}/document/post",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> getPosts(@PathVariable Long userId){
        log.info("Inside getPost controller");
        try {
            return ResponseEntity.ok().body(new Response<>(Constant.SUCCESS_RESPONSE_MESSAGE, HttpStatus.OK.value(), userDocumentCommand.getPosts(userId)));
        }catch (Exception e){
            return ResponseEntity.ok().body(new Response<>(Constant.ERROR_RESPONSE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }
}
