package com.bank.user.application.openfeign;

import com.bank.user.application.user.model.CommentResponse;
import com.bank.user.application.user.model.PostResponse;
import com.bank.user.application.user.request.PostCommentRequest;
import com.bank.user.application.user.request.PostRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "thirdPartyAPI",url = "${thirdParty.API}",fallback = ThirdPartyAPIFallback.class)
public interface ThirdPartyAPI {

    @RequestMapping(method = RequestMethod.GET, value = "/posts?userId={userId}", produces = "application/json")
    List<PostResponse> getPosts(@PathVariable Long userId);

    @RequestMapping(method = RequestMethod.POST, value = "/posts", produces = "application/json")
    PostResponse createPost(@RequestBody PostRequest data);

    @RequestMapping(method = RequestMethod.POST, value = "/comments", produces = "application/json")
    CommentResponse createComment(@RequestBody PostCommentRequest data);
}



