package com.e2e.api.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ProductsApi {

    public Response getAllProducts() {
        return ApiBase.spec().get("/api/productsList");
    }

    public Response searchProduct(String term) {
        return ApiBase.spec()
                .contentType(ContentType.URLENC)
                .formParam("search_product", term)
                .post("/api/searchProduct");
    }

    public Response searchProductMissingParam() {
        return ApiBase.spec()
                .contentType(ContentType.URLENC)
                .post("/api/searchProduct");
    }
    public Response postAllProductsList() {
        return ApiBase.spec().post("/api/productsList");
    }

}
