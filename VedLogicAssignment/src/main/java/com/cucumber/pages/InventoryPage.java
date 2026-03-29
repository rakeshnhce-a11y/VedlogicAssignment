package com.cucumber.pages;

import com.cucumber.commonservices.ElementUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    private WebDriver driver;
    private ElementUtility util;

    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.util = new ElementUtility(driver);
    }

    public void addItem(String itemName) {
        By addBtn = By.xpath("//div[text()='" + itemName + "']/ancestor::div[@class='inventory_item']//button");
        util.click(addBtn);
    }

    public int getCartCount() {
        return Integer.parseInt(util.getText(cartBadge));
    }

    public void clickCart() {
        util.click(cartLink);
    }
}