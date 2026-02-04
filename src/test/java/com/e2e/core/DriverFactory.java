package com.e2e.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
    private static final ThreadLocal<WebDriver> TL = new ThreadLocal<>();

    public static WebDriver get() {
        WebDriver d = TL.get();
        if (d == null) throw new IllegalStateException("WebDriver not initialized for this thread");
        return d;
    }

    public static void createDriver() {
        if (TL.get() != null) return;

        String browser = Config.browser().toLowerCase().trim();
        boolean headless = Config.headless();
        WebDriver driver;

        switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("-headless");
                driver = new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser + " (use chrome or firefox)");
        }

        // maximize (headless needs a fixed size)
        if (!headless) {
            driver.manage().window().maximize();
        } else {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }

        TL.set(driver);
    }

    public static void quitDriver() {
        WebDriver d = TL.get();
        if (d != null) {
            d.quit();
            TL.remove();
        }
    }

    private DriverFactory() {}
}
