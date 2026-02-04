package com.e2e.api.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class UsersApi {

    public Response createAccount(Map<String, String> form) {
        return ApiBase.spec()
                .contentType(ContentType.URLENC)
                .formParams(form)
                .post("/api/createAccount");
    }

    public Response deleteAccount(String email, String password) {
        return ApiBase.spec()
                .contentType(ContentType.URLENC)
                .formParam("email", email)
                .formParam("password", password)
                .delete("/api/deleteAccount");
    }

    public Response verifyLogin(String email, String password) {
        return ApiBase.spec()
                .contentType(ContentType.URLENC)
                .formParam("email", email)
                .formParam("password", password)
                .post("/api/verifyLogin");
    }

    public Response verifyLoginMissingEmail(String password) {
        return ApiBase.spec()
                .contentType(ContentType.URLENC)
                .formParam("password", password)
                .post("/api/verifyLogin");
    }

    public Response getUserDetailByEmail(String email) {
        return ApiBase.spec()
                .queryParam("email", email)
                .get("/api/getUserDetailByEmail");
    }

    public Response deleteVerifyLogin() {
        return ApiBase.spec().delete("/api/verifyLogin");
    }
}
