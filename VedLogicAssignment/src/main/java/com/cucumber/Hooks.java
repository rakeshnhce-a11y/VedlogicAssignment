package com.cucumber;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.cucumber.core.DriverFactory;
import com.cucumber.core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    public static Scenario scenario;

    @Before
    public void defaultSetup(Scenario scenario) {

        Hooks.scenario = scenario;

        System.out.println("Tags: " + scenario.getSourceTagNames());

        boolean isApiTest = scenario.getSourceTagNames()
                .stream()
                .anyMatch(tag -> tag.equalsIgnoreCase("@API"));

        if (isApiTest) {
            System.out.println("API Test - Skipping browser launch ");
            return;
        }

        System.out.println("UI Test - Launching browser ");


        DriverManager.setDriver(
                DriverFactory.initDriver("chrome")
        );

        DriverManager.getDriver().get("https://www.saucedemo.com/");
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            ExtentTest currentStep = ExtentCucumberAdapter.getCurrentStep();
            if (currentStep != null) {
                currentStep.fail("Test failed: " + scenario.getName());
            }
        }

        boolean isApiTest = scenario.getSourceTagNames()
                .stream()
                .anyMatch(tag -> tag.equalsIgnoreCase("@API"));

        if (!isApiTest && DriverManager.getDriver() != null) {
            System.out.println("UI Test - Quitting browser");
            DriverManager.quitDriver();
        } else {
            System.out.println("API Test - No browser teardown needed");
        }
    }
}