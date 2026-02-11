package com.e2e.ui.tests;

import com.e2e.api.client.UsersApi;
import com.e2e.core.BaseUiTest;
import com.e2e.core.Config;
import com.e2e.ui.pages.*;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UiTests extends BaseUiTest {

    private final UsersApi usersApi = new UsersApi();

    private Map<String, String> newUserForm(String email, String password) {
        Map<String, String> f = new HashMap<>();
        f.put("name", "UiUser");
        f.put("email", email);
        f.put("password", password);
        f.put("title", "Mr");
        f.put("birth_date", "1");
        f.put("birth_month", "1");
        f.put("birth_year", "2000");
        f.put("firstname", "Ui");
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

    private String[] createUserViaApi() {
        String email = "ui" + System.currentTimeMillis() + "@example.com";
        String password = "correct!";
        usersApi.createAccount(newUserForm(email, password));
        return new String[]{email, password};
    }

    @Test
    public void loginUser_correctEmailPassword() {
        var creds = createUserViaApi();

        HomePage home = new HomePage(driver, waits).open();
        SignupLoginPage login = home.goToSignupLogin();

        login.assertLoginVisible();
        login.login(creds[0], creds[1]);
        login.assertLoggedIn();

        login.deleteAccountFromUi();
    }

    @Test
    public void loginUser_incorrectEmailPassword() {
        HomePage home = new HomePage(driver, waits).open();
        SignupLoginPage login = home.goToSignupLogin();

        login.assertLoginVisible();
        login.login("bad" + System.currentTimeMillis() + "@example.com", "wrong");
        login.assertLoginError();
    }

    @Test
    public void logoutUser() {
        var creds = createUserViaApi();

        HomePage home = new HomePage(driver, waits).open();
        SignupLoginPage login = home.goToSignupLogin();

        login.login(creds[0], creds[1]);
        login.assertLoggedIn();

        login.logout();
        login.assertLoginVisible();
    }

    @Test
    public void registerUser_existingEmail() {
        var creds = createUserViaApi();

        HomePage home = new HomePage(driver, waits).open();
        SignupLoginPage page = home.goToSignupLogin();

        page.assertSignupVisible();
        page.signupBasic("SomeName", creds[0]);
        page.assertExistingEmailError();
    }

    @Test
    public void contactUsForm() {
        driver.get(Config.baseUrl() + "/contact_us");
        ContactUsPage cu = new ContactUsPage(driver, waits);

        cu.assertVisible();
        cu.submitForm(
                "Name",
                "email" + System.currentTimeMillis() + "@example.com",
                "Subject",
                "Message",
                "contact_upload.txt"
        );
    }

    @Test
    public void verifyTestCasesPage() {
        HomePage home = new HomePage(driver, waits).open();
        TestCasesPage tc = home.goToTestCases();
        tc.assertVisible();
    }

    @Test
    public void verifyAllProducts_and_detailPage() {
        HomePage home = new HomePage(driver, waits).open();
        ProductsPage products = home.goToProducts();

        products.assertAllProductsVisible();
        ProductDetailPage detail = products.openFirstProductDetail();
        detail.assertDetailsVisible();
    }

    @Test
    public void searchProduct() {
        HomePage home = new HomePage(driver, waits).open();
        ProductsPage products = home.goToProducts();

        products.assertAllProductsVisible();
        products.search("top");
    }

    @Test
    public void verifySubscription_homePage() {
        HomePage home = new HomePage(driver, waits).open();
        home.subscribe("sub" + System.currentTimeMillis() + "@example.com");
    }

    @Test
    public void addProductsInCart() {
        HomePage home = new HomePage(driver, waits).open();
        ProductsPage products = home.goToProducts();

        products.assertAllProductsVisible();
        CartPage cart = products.addTwoProductsAndOpenCart();
        cart.assertMinItems(2);
    }
}
