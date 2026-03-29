package com.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "classpath:Features",
        glue = {"com.cucumber"},
        tags = "@UIAutomationForSaucedemo",
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }

)


public class TestRunner extends AbstractTestNGCucumberTests {

}