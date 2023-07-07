package com.bank.user;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDocumentIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void createPost() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body= "{\"userDocumentId\":\"cc1024dc-f01e-49be-8efd-c883d1714919\",\"description\":\"test 33\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/user/1/document/post"),
                HttpMethod.POST, entity, String.class);
        String expected = "{\"message\":\"SUCCESS\",\"httpStatus\":200,\"payload\":{\"id\":3,\"userId\":1,\"description\":\"test 33\",\"userDocumentId\":cc1024dc-f01e-49be-8efd-c883d1714919}}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void createComment() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        String body= "{\"postId\":1,\"comment\":\"test 34\"}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/user/1/document/post/Comment"),
                HttpMethod.POST, entity, String.class);
        String expected = "{\"message\":\"SUCCESS\",\"httpStatus\":200,\"payload\":{\"id\":3,\"postId\":1,\"userId\":1,\"comment\":\"test 34\"}}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getPosts() throws JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/user/1/document/post"),
                HttpMethod.GET, entity, String.class);
        String expected = "{\"message\":\"SUCCESS\",\"httpStatus\":200,\"payload\":[{\"id\":1,\"userId\":1,\"description\":\"Test\",\"userDocumentId\":\"cb3c8e39-fbb9-4ff3-bfb1-3b288e5b7eb8\"},{\"id\":2,\"userId\":1,\"description\":\"Test1\",\"userDocumentId\":\"ad3c8e39-fbb9-4ff3-bfb1-3b288e5b7eb8\"}]}";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

