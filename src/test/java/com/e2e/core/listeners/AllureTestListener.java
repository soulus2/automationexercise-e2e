package com.e2e.core.listeners;

import com.e2e.core.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class AllureTestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            WebDriver d = DriverFactory.get();
            attachScreenshot(d);
            attachPageSource(d);
        } catch (Exception ignored) {}
        attachThrowable(result.getThrowable());
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] attachScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page Source", type = "text/html")
    public String attachPageSource(WebDriver driver) {
        return driver.getPageSource();
    }

    @Attachment(value = "Error", type = "text/plain")
    public String attachThrowable(Throwable t) {
        return t == null ? "" : t.toString();
    }
}
