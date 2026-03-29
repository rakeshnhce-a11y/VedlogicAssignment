package com.cucumber.pages;

import com.cucumber.commonservices.ElementUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {

    private WebDriver driver;
    private ElementUtility util;

    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueBtn = By.id("continue");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.util = new ElementUtility(driver);
    }

    public void enterDetails(String first, String last, String zip) {
        util.type(firstName, first);
        util.type(lastName, last);
        util.type(postalCode, zip);
    }

    public void clickContinue() {
        util.click(continueBtn);
    }
}