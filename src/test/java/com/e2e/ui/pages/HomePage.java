package com.e2e.ui.pages;

import com.e2e.core.Config;
import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;
    private final Waits waits;

    private final By signupLogin = By.cssSelector("a[href='/login']");
    private final By testCases = By.cssSelector("a[href='/test_cases']");
    private final By products = By.cssSelector("a[href='/products']");
    private final By contactUs = By.cssSelector("a[href='/contact_us']");

    // footer subscription
    private final By subscriptionTitle = By.xpath("//h2[normalize-space()='Subscription']");
    private final By subscriptionEmail = By.id("susbscribe_email");
    private final By subscriptionBtn = By.id("subscribe");
    private final By subscriptionSuccess = By.xpath("//*[contains(.,'You have been successfully subscribed')]");

    public HomePage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Open home page")
    public HomePage open() {
        driver.get(Config.baseUrl());
        return this;
    }

    @Step("Go to Signup/Login")
    public SignupLoginPage goToSignupLogin() {
        waits.clickable(signupLogin).click();
        return new SignupLoginPage(driver, waits);
    }

    @Step("Go to Test Cases")
    public TestCasesPage goToTestCases() {
        waits.clickable(testCases).click();
        return new TestCasesPage(driver, waits);
    }

    @Step("Go to Products")
    public ProductsPage goToProducts() {
        waits.clickable(products).click();
        return new ProductsPage(driver, waits);
    }

    @Step("Go to Contact Us")
    public ContactUsPage goToContactUs() {
        waits.clickable(contactUs).click();
        return new ContactUsPage(driver, waits);
    }

    @Step("Subscribe with email: {email}")
    public void subscribe(String email) {
        waits.scrollToBottom(driver);
        waits.visible(subscriptionTitle);
        waits.visible(subscriptionEmail).clear();
        waits.visible(subscriptionEmail).sendKeys(email);
        waits.clickable(subscriptionBtn).click();
        waits.visible(subscriptionSuccess);
    }
}
