package com.e2e.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class Waits {
    private final WebDriverWait wait;

    public Waits(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.timeoutSeconds()));
    }

    public WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement clickable(WebElement el) {
        return wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public boolean urlContains(WebDriver d, String part) {
        return wait.until(ExpectedConditions.urlContains(part));
    }

    public void scrollToBottom(WebDriver d) {
        ((JavascriptExecutor) d).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public void scrollIntoViewCenter(WebDriver d, WebElement el) {
        ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }
}
