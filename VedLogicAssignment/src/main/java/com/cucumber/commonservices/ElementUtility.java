package com.cucumber.commonservices;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ElementUtility {

    private WebDriver driver;
    private WebDriverWait wait;

    public ElementUtility(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void type(By locator, String value) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    public String getText(By locator) {
        return getElement(locator).getText();
    }

    public int getElementsCount(By locator) {
        return driver.findElements(locator).size();
    }
}
