package com.e2e.api.client;

import io.restassured.response.Response;

public class BrandsApi {
    public Response getAllBrands() {
        return ApiBase.spec().get("/api/brandsList");
    }
    public Response putAllBrandsList() {
        return ApiBase.spec().put("/api/brandsList");
    }


}
