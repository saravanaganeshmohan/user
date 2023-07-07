package com.bank.user.util;

import com.bank.user.application.domain.UserDocument;
import com.bank.user.application.user.model.PostResponse;
import com.bank.user.application.user.model.UserDocumentResponse;
import com.bank.user.application.user.request.PostRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.time.Instant;
import java.util.List;

public class JsonConverter {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantConverter()).enableComplexMapKeySerialization().setPrettyPrinting().create();


    public static List<UserDocumentResponse> fromUserDocumentsResponse(List<UserDocument> userDocuments) {
        return GSON.fromJson(GSON.toJson(userDocuments), new TypeToken<List<UserDocumentResponse>>() {
        }.getType());
    }

    public static UserDocumentResponse fromUserDocumentResponse(UserDocument userDocuments) {
        return GSON.fromJson(GSON.toJson(userDocuments), new TypeToken<UserDocumentResponse>() {
        }.getType());
    }

    public static String toPostRequest(PostRequest postRequest) {
        if (postRequest == null) {
            return null;
        }
        return GSON.toJson(postRequest, new TypeToken<PostRequest>() {
        }.getType());
    }

    public static String toPostResponse(PostResponse postResponse) {
        if (postResponse == null) {
            return null;
        }
        return GSON.toJson(postResponse, new TypeToken<PostResponse>() {
        }.getType());
    }
}
