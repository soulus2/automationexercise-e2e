package com.e2e.core;

import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Listeners({AllureTestNg.class})
public abstract class BaseUiTest {
    protected WebDriver driver;
    protected Waits waits;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.createDriver();
        driver = DriverFactory.get();
        waits = new Waits(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE && driver != null) {
                byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Failure Screenshot", "image/png", new ByteArrayInputStream(png), ".png");

                byte[] html = driver.getPageSource().getBytes(StandardCharsets.UTF_8);
                Allure.addAttachment("Page Source", "text/html", new ByteArrayInputStream(html), ".html");
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
