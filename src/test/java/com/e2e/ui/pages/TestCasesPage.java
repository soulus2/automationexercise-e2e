package com.e2e.ui.pages;

import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TestCasesPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By title = By.xpath("//h2[contains(@class,'title') and contains(normalize-space(),'Test Cases')]");

    public TestCasesPage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Assert Test Cases page visible")
    public void assertVisible() {
        waits.urlContains(driver, "/test_cases");
        waits.visible(title);
    }
}
