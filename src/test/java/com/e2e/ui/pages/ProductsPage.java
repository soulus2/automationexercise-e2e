package com.e2e.ui.pages;

import com.e2e.core.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class ProductsPage {
    private final WebDriver driver;
    private final Waits waits;

    private final By allProductsTitle = By.xpath("//h2[normalize-space()='All Products']");
    private final By firstViewProduct = By.cssSelector("a[href^='/product_details/']");

    private final By searchInput = By.id("search_product");
    private final By searchBtn = By.id("submit_search");
    private final By searchedProductsTitle = By.xpath("//h2[normalize-space()='Searched Products']");

    // product cards + add to cart
    private final By productCards = By.cssSelector(".features_items .product-image-wrapper");
    private final By addToCartInCard = By.cssSelector("a.add-to-cart");

    // modal buttons
    private final By continueShopping = By.xpath("//button[normalize-space()='Continue Shopping']");
    private final By viewCart = By.xpath("//a[normalize-space()='View Cart' or .//u[normalize-space()='View Cart']]");

    public ProductsPage(WebDriver driver, Waits waits) {
        this.driver = driver;
        this.waits = waits;
    }

    @Step("Assert Products page visible")
    public void assertAllProductsVisible() {
        waits.urlContains(driver, "/products");
        waits.visible(allProductsTitle);
    }

    @Step("Open first product detail page")
    public ProductDetailPage openFirstProductDetail() {
        waits.clickable(firstViewProduct).click();
        return new ProductDetailPage(driver, waits);
    }

    @Step("Search for product: {term}")
    public void search(String term) {
        waits.visible(searchInput).clear();
        waits.visible(searchInput).sendKeys(term);
        waits.clickable(searchBtn).click();
        waits.visible(searchedProductsTitle);
    }

    @Step("Add 2 products to cart and open cart")
    public CartPage addTwoProductsAndOpenCart() {
        clickAddToCartByCardIndex(0);
        waits.clickable(continueShopping).click();
        clickAddToCartByCardIndex(1);
        waits.clickable(viewCart).click();
        return new CartPage(driver, waits);
    }

    private void clickAddToCartByCardIndex(int idx) {
        waitCardsAtLeast(idx + 1);
        List<WebElement> cards = driver.findElements(productCards);
        WebElement card = cards.get(idx);

        waits.scrollIntoViewCenter(driver, card);
        new Actions(driver).moveToElement(card).perform();

        WebElement addBtn = card.findElement(addToCartInCard);

        // sometimes overlay button becomes clickable only after hover + small scroll
        waits.scrollIntoViewCenter(driver, addBtn);

        try {
            waits.clickable(addBtn).click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
        }
    }

    private void waitCardsAtLeast(int min) {
        long end = System.currentTimeMillis() + 15000;
        while (System.currentTimeMillis() < end) {
            if (driver.findElements(productCards).size() >= min) return;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        throw new NoSuchElementException("Expected at least " + min + " product cards, but found " +
                driver.findElements(productCards).size());
    }
}
