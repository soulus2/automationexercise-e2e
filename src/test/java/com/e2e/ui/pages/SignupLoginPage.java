package com.e2e.ui.pages;

import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupLoginPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By loginTitle = By.xpath("//*[contains(.,'Login to your account')]");
    private final By loginEmail = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginBtn = By.cssSelector("button[data-qa='login-button']");
    private final By loginError = By.xpath("//*[contains(.,'Your email or password is incorrect')]");

    private final By signupTitle = By.xpath("//*[contains(.,'New User Signup!')]");
    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupBtn = By.cssSelector("button[data-qa='signup-button']");
    private final By signupErrorExisting = By.xpath("//*[contains(.,'Email Address already exist')]");

    private final By loggedInAs = By.xpath("//*[contains(.,'Logged in as')]");
    private final By logout = By.cssSelector("a[href='/logout']");
    private final By deleteAccount = By.cssSelector("a[href='/delete_account']");
    private final By accountDeleted = By.xpath(
            "//*[self::h2 or self::b][contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'ACCOUNT DELETED')]"
    );
    private final By continueBtn = By.xpath("//a[normalize-space()='Continue'] | //button[normalize-space()='Continue']");

    public SignupLoginPage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Assert login section visible")
    public void assertLoginVisible() {
        waits.visible(loginTitle);
    }

    @Step("Login with email/password")
    public void login(String email, String password) {
        waits.visible(loginEmail).clear();
        waits.visible(loginEmail).sendKeys(email);
        waits.visible(loginPassword).clear();
        waits.visible(loginPassword).sendKeys(password);
        waits.clickable(loginBtn).click();
    }

    @Step("Assert login error visible")
    public void assertLoginError() {
        waits.visible(loginError);
    }

    @Step("Assert signup section visible")
    public void assertSignupVisible() {
        waits.visible(signupTitle);
    }

    @Step("Attempt signup with existing email")
    public void signupBasic(String name, String email) {
        waits.visible(signupName).clear();
        waits.visible(signupName).sendKeys(name);
        waits.visible(signupEmail).clear();
        waits.visible(signupEmail).sendKeys(email);
        waits.clickable(signupBtn).click();
    }

    @Step("Assert existing email error visible")
    public void assertExistingEmailError() {
        waits.visible(signupErrorExisting);
    }

    @Step("Assert user is logged in")
    public void assertLoggedIn() {
        waits.visible(loggedInAs);
    }

    @Step("Logout")
    public void logout() {
        waits.clickable(logout).click();
        waits.visible(loginTitle);
    }

    @Step("Delete account from UI")
    public void deleteAccountFromUi() {
        var el = waits.clickable(deleteAccount);

        // make sure it's actually clicked (Firefox sometimes needs scroll/JS)
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
            el.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        // ensure navigation happened
        waits.urlContains(driver, "/delete_account");

        // now wait for the confirmation text (case-insensitive)
        waits.visible(accountDeleted);

        // optional: click Continue if present
        if (!driver.findElements(continueBtn).isEmpty()) {
            waits.clickable(continueBtn).click();
        }
    }

}
