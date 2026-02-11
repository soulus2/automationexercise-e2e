package com.e2e.api.tests;

import com.e2e.api.client.BrandsApi;
import com.e2e.api.client.ProductsApi;
import com.e2e.api.client.UsersApi;
import com.e2e.core.BaseApiTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class ApiTests extends BaseApiTest {

    private final ProductsApi productsApi = new ProductsApi();
    private final BrandsApi brandsApi = new BrandsApi();
    private final UsersApi usersApi = new UsersApi();

    private void assertMethodNotSupported(io.restassured.response.Response res) {
        int http = res.statusCode();

        // Some endpoints return HTTP 405, others return HTTP 200 with JSON {responseCode:405}
        if (http == 405) return;

        if (http == 200) {
            int rc = res.jsonPath().getInt("responseCode");
            String msg = String.valueOf(res.jsonPath().getString("message")).toLowerCase();
            org.testng.Assert.assertEquals(rc, 405);
            org.testng.Assert.assertTrue(msg.contains("not supported"));
            return;
        }

        org.testng.Assert.fail("Expected 405 (or 200 with responseCode=405), got HTTP " + http + " body=" + res.asString());
    }

    private Map<String, String> newUserForm(String email, String password) {
        Map<String, String> f = new HashMap<>();
        f.put("name", "TestUser");
        f.put("email", email);
        f.put("password", password);
        f.put("title", "Mr");
        f.put("birth_date", "1");
        f.put("birth_month", "1");
        f.put("birth_year", "2000");
        f.put("firstname", "Test");
        f.put("lastname", "User");
        f.put("company", "Uni");
        f.put("address1", "Street 1");
        f.put("address2", "Street 2");
        f.put("country", "Canada");
        f.put("zipcode", "00000");
        f.put("state", "State");
        f.put("city", "City");
        f.put("mobile_number", "1234567890");
        return f;
    }

    @Test
    public void getAllProductsList() {
        var res = productsApi.getAllProducts();
        assertEquals(res.statusCode(), 200);
        assertTrue(res.asString().toLowerCase().contains("products"));
    }

    @Test
    public void getAllBrandsList() {
        var res = brandsApi.getAllBrands();
        assertEquals(res.statusCode(), 200);
        assertTrue(res.asString().toLowerCase().contains("brands"));
    }

    @Test
    public void searchProduct_valid() {
        var res = productsApi.searchProduct("top");
        assertEquals(res.statusCode(), 200);
        assertTrue(res.asString().toLowerCase().contains("products"));
    }

    @Test
    public void searchProduct_missingParam() {
        var res = productsApi.searchProductMissingParam();
        assertEquals(res.statusCode(), 200);
        assertEquals(res.jsonPath().getInt("responseCode"), 400);
    }

    @Test
    public void verifyLogin_missingEmail() {
        var res = usersApi.verifyLoginMissingEmail("whatever");
        assertEquals(res.statusCode(), 200);
        assertEquals(res.jsonPath().getInt("responseCode"), 400);
    }

    @Test
    public void verifyLogin_invalidDetails() {
        var res = usersApi.verifyLogin("nope" + System.currentTimeMillis() + "@example.com", "wrong");
        assertEquals(res.statusCode(), 200);
        assertEquals(res.jsonPath().getInt("responseCode"), 404);
    }

    @Test
    public void userLifeCycle() {
        String email = "user" + System.currentTimeMillis() + "@example.com";
        String password = "Pass1234!";

        var create = usersApi.createAccount(newUserForm(email, password));
        assertEquals(create.statusCode(), 200);
        assertEquals(create.jsonPath().getInt("responseCode"), 201);

        var verify = usersApi.verifyLogin(email, password);
        assertEquals(verify.statusCode(), 200);
        assertEquals(verify.jsonPath().getInt("responseCode"), 200);

        var detail = usersApi.getUserDetailByEmail(email);
        assertEquals(detail.statusCode(), 200);
        assertEquals(detail.jsonPath().getInt("responseCode"), 200);

        var del = usersApi.deleteAccount(email, password);
        assertEquals(del.statusCode(), 200);
        assertEquals(del.jsonPath().getInt("responseCode"), 200);
    }

    @Test
    public void postAllProductsList_methodNotSupported() {
        var res = productsApi.postAllProductsList();
        assertMethodNotSupported(res);
    }

    @Test
    public void putAllBrandsList_methodNotSupported() {
        var res = brandsApi.putAllBrandsList();
        assertMethodNotSupported(res);
    }

    @Test
    public void deleteVerifyLogin_methodNotSupported() {
        var res = usersApi.deleteVerifyLogin();
        assertMethodNotSupported(res);
    }


}
