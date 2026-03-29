package com.cucumber.pages;
import com.cucumber.commonservices.ElementUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private ElementUtility util;

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.util = new ElementUtility(driver);
    }

    public void navigate(String url) {
        driver.get(url);
        System.out.println("Navigated to: " + driver.getCurrentUrl());
    }

    public void login(String user, String pass) {
        util.type(username, user);
        util.type(password, pass);
        util.click(loginBtn);
    }
}