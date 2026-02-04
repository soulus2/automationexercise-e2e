package com.e2e.ui.pages;

import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By productInfoBox = By.cssSelector(".product-information");
    private final By productName = By.cssSelector(".product-information h2");
    private final By category = By.xpath("//*[contains(.,'Category')]");
    private final By price = By.xpath("//*[contains(.,'Rs.') or contains(.,'Price')]");
    private final By availability = By.xpath("//*[contains(.,'Availability')]");
    private final By condition = By.xpath("//*[contains(.,'Condition')]");
    private final By brand = By.xpath("//*[contains(.,'Brand')]");

    public ProductDetailPage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Assert product details visible")
    public void assertDetailsVisible() {
        waits.urlContains(driver, "/product_details/");
        waits.visible(productInfoBox);
        waits.visible(productName);
        waits.visible(category);
        waits.visible(price);
        waits.visible(availability);
        waits.visible(condition);
        waits.visible(brand);
    }
}
