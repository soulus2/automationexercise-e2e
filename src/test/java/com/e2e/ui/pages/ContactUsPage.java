package com.e2e.ui.pages;

import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Paths;

public class ContactUsPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By getInTouchTitle = By.xpath("//h2[normalize-space()='Get In Touch']");

    private final By name = By.cssSelector("input[data-qa='name']");
    private final By email = By.cssSelector("input[data-qa='email']");
    private final By subject = By.cssSelector("input[data-qa='subject']");
    private final By message = By.cssSelector("textarea[data-qa='message']");
    private final By upload = By.name("upload_file");
    private final By submit = By.cssSelector("input[data-qa='submit-button']");

    private final By success = By.xpath("//*[contains(.,'Success! Your details have been submitted successfully')]");

    public ContactUsPage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Assert Contact Us page visible")
    public void assertVisible() {
        waits.urlContains(driver, "/contact_us");
        waits.visible(getInTouchTitle);
    }

    @Step("Submit contact form (with upload)")
    public void submitForm(String n, String e, String s, String m, String resourceFileName) {
        waits.visible(name).clear();
        waits.visible(name).sendKeys(n);

        waits.visible(email).clear();
        waits.visible(email).sendKeys(e);

        waits.visible(subject).clear();
        waits.visible(subject).sendKeys(s);

        waits.visible(message).clear();
        waits.visible(message).sendKeys(m);

        String path = Paths.get("src/test/resources/files/" + resourceFileName).toAbsolutePath().toString();
        waits.visible(upload).sendKeys(path);

        waits.clickable(submit).click();

        // site shows JS alert confirmation
        driver.switchTo().alert().accept();

        waits.visible(success);
    }
}
