package com.cucumber;

import com.cucumber.core.DriverManager;
import com.cucumber.pages.*;
import com.cucumber.commonservices.PriceUtility;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class CheckoutStepDef {

    LoginPage login;
    InventoryPage inventory;
    CartPage cart;
    CheckoutPage checkout;
    CheckoutOverviewPage overview;
    PriceUtility utility;

    String firstName = "Rakesh", lastName = "C", zip = "82001";


    public CheckoutStepDef() {

        login = new LoginPage(DriverManager.getDriver());
        inventory = new InventoryPage(DriverManager.getDriver());
        cart = new CartPage(DriverManager.getDriver());
        checkout = new CheckoutPage(DriverManager.getDriver());
        overview = new CheckoutOverviewPage(DriverManager.getDriver());

        utility = new PriceUtility();
    }

    @Given("I am on the home page")
    public void openHomePage() {
        login.navigate("https://www.saucedemo.com/");
    }

    @Given("I login in with the following details")
    public void login(DataTable table) {
        Map<String, String> data = table.asMaps(String.class, String.class).get(0);
        login.login(data.get("userName"), data.get("Password"));
    }

    @Given("I add the following items to the basket")
    public void addItems(DataTable table) {
        List<String> items = table.asList();
        for (String item : items) {
            inventory.addItem(item);
        }
    }

    @Given("I  should see {int} items added to the shopping cart")
    public void verifyCartCount(int expected) {
        assertEquals(expected, inventory.getCartCount());
    }

    @Given("I click on the shopping cart")
    public void clickCart() {
        inventory.clickCart();
    }

    @Given("I verify that the QTY count for each item should be 1")
    public void verifyQty() {
        cart.verifyQuantityIsOne();
    }

    @Given("I remove the following item:")
    public void removeItem(DataTable table) {
        List<String> items = table.asList();
        for (String item : items) {
            cart.removeItem(item);
        }
    }

    @Given("I click on the CHECKOUT button")
    public void clickCheckout() {
        cart.clickCheckout();
    }

    @Given("I type {string} for First Name")
    public void enterFirstName(String fName) {
        this.firstName = fName;
    }

    @Given("I type {string} for Last Name")
    public void enterLastName(String lName) {
        this.lastName = lName;
    }

    @When("I click on the CONTINUE button")
    public void clickContinue() {
        checkout.enterDetails(firstName, lastName, zip);
        checkout.clickContinue();
    }

    @Then("Item total will be equal to the total of items on the list")
    public void validateTotal() {
        List<Double> prices = overview.getItemPrices();
        double expected = utility.calculateTotal(prices);
        double actual = overview.getItemTotal();

        assertEquals(expected, actual, 0.01);
    }

    @Then("a Tax rate of {int} % is applied to the total")
    public void validateTax(int taxRate) {
        List<Double> prices = overview.getItemPrices();
        double total = utility.calculateTotal(prices);

        double expectedTax = utility.calculateTax(total, taxRate / 100.0);
        double actualTax = overview.getTax();

        assertEquals(expectedTax, actualTax, 0.01);
    }

    @And("I type {string} for ZIP Code")
    public void enterZip(String zip) {
        this.zip = zip;
    }
}