package com.cucumber.pages;

import com.cucumber.commonservices.ElementUtility;
import org.openqa.selenium.*;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class CartPage {

    private WebDriver driver;
    private ElementUtility util;

    private By qty = By.className("cart_quantity");
    private By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.util = new ElementUtility(driver);
    }

    public void verifyQuantityIsOne() {

        List<WebElement> qtyList = driver.findElements(qty);

        for (WebElement element : qtyList) {
            assertEquals(element.getText(), "1");
        }
    }

    public void removeItem(String itemName) {
        By removeBtn = By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='cart_item']//button");
        util.click(removeBtn);
    }

    public void clickCheckout() {
        util.click(checkoutBtn);
    }
}