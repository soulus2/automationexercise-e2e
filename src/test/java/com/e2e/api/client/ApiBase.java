package com.e2e.api.client;

import com.e2e.core.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public final class ApiBase {
    private ApiBase() {}

    public static RequestSpecification spec() {
        return given()
                .baseUri(Config.apiBaseUrl())
                .filter(new AllureRestAssured());
    }
}
