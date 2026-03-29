The framework follows enterprise-level best practices including:

Thread-safe WebDriver management
Page Object Model (POM)
Service layer for API
Reusable utility components
Tag-based execution (UI vs API)

Key Features
1. Thread-Safe Driver Management
Implemented using ThreadLocal
Supports parallel execution
DriverManager.setDriver(DriverFactory.initDriver("chrome"));
2. Constructor Injection (No Base Class)
public LoginPage(WebDriver driver) {
this.driver = driver;
}

Loose coupling
Better maintainability

3. Reusable Element Utility

All UI actions centralized:

util.click(locator);
util.type(locator, value);
util.getText(locator);

Reduces duplication
Improves stability

4. Hybrid Execution (UI + API)
UI tests → Browser launched
API tests → No browser
@UI
Scenario: UI Test

@API
Scenario: API Test
5. Clean Hooks Design
if (isApiTest) {
// Skip browser
} else {
// Launch browser
}


Tech Stack used as follows:
Selenium - UI Automation
Cucumber - BDD
TestNG - Test Execution
Rest Assured - API Testing
WebDriverManager - Driver setup
Extent Reports - Reporting