package com.e2e.ui.pages;

import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By cartTable = By.id("cart_info_table");
    private final By cartRows = By.cssSelector("#cart_info_table tbody tr");

    public CartPage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Assert cart has at least {min} items")
    public void assertMinItems(int min) {
        waits.urlContains(driver, "/view_cart");
        waits.visible(cartTable);
        int rows = driver.findElements(cartRows).size();
        if (rows < min) throw new AssertionError("Expected at least " + min + " cart rows, got " + rows);
    }
}
