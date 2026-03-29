package com.cucumber.pages;

import com.cucumber.commonservices.ElementUtility;
import org.openqa.selenium.*;
import java.util.*;
import java.util.stream.Collectors;

public class CheckoutOverviewPage {

    private WebDriver driver;
    private ElementUtility util;

    private By itemPrices = By.className("inventory_item_price");
    private By totalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.util = new ElementUtility(driver);
    }

    public List<Double> getItemPrices() {

        List<WebElement> priceElements = driver.findElements(itemPrices);

        return priceElements.stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    public double getItemTotal() {
        return Double.parseDouble(util.getText(totalLabel).replace("Item total: $", ""));
    }

    public double getTax() {
        return Double.parseDouble(util.getText(taxLabel).replace("Tax: $", ""));
    }
}