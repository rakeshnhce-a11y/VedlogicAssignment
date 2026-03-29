package com.cucumber;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.Assert;

public class ReqResStepDef {

    private Response response;
    private final String BASE_URL = "https://reqres.in";

    private String requestName;
    private String requestJob;

    // 🔹 Reusable Request Spec
    private RequestSpecification requestSpec() {
        return given()
                .baseUri(BASE_URL)
                .header("x-api-key", System.getProperty("apiKey", "reqres_becd6a562fab48e4a8eed3a82973756f"))
                .contentType("application/json")
                .log().all();
    }

    // =========================
    // LIST USERS
    // =========================

    @Given("users requested for page {int}")
    public void usersRequestedForPage(int page) {

    }

    @When("I request users for page {int}")
    public void requestUsers(int page) {

        response = requestSpec()
                .queryParam("page", page)
                .when()
                .get("/api/users")
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the number of users returned should match the per_page count")
    public void validateUserCount() {

        List<Integer> ids = response.jsonPath().getList("data.id");
        int perPage = response.jsonPath().getInt("per_page");

        assertEquals(ids.size(), perPage);
    }

    // =========================
    // SINGLE USER
    // =========================

    @Given("I request user with id {int}")
    public void requestSingleUser(int userId) {

        response = requestSpec()
                .when()
                .get("/api/users/" + userId)
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the response should contain user details")
    public void validateUserDetails(DataTable table) {

        Map<String, String> expected = table.asMaps().get(0);

        assertEquals(response.jsonPath().getString("data.first_name"),
                expected.get("first_name"));

        assertEquals(response.jsonPath().getString("data.email"),
                expected.get("email"));
    }

    // =========================
    // USER NOT FOUND
    // =========================

    @Then("the response status should be {int}")
    public void validateStatusCode(int expectedCode) {

        assertEquals(response.getStatusCode(), expectedCode);
    }

    // =========================
    // CREATE USER
    // =========================

    @Given("I create a user with {string} and {string}")
    public void createUser(String name, String job) {

        this.requestName = name;
        this.requestJob = job;

        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("job", job);

        response =
                given()
                        .baseUri("https://reqres.in")   // ✅ IMPORTANT
                        .header("Content-Type", "application/json")
                        .header("x-api-key", "reqres_becd6a562fab48e4a8eed3a82973756f")
                        .body(body)
                        .when()
                        .post("/api/users")
                        .then()
                        .log().all()
                        .extract().response();
    }


    // =========================
    // LOGIN
    // =========================

    @Given("I send login request with the following data")
    public void login(DataTable table) {

        Map<String, String> data = table.asMaps().get(0);

        Map<String, String> body = new HashMap<>();
        body.put("email", data.get("Email"));

        // Handle optional password
        if (data.get("Password") != null && !data.get("Password").isEmpty()) {
            body.put("password", data.get("Password"));
        }

        response = requestSpec()
                .body(body)
                .when()
                .post("/api/login")
                .then()
                .log().all()
                .extract().response();
    }

    @Then("the response error message should be {string}")
    public void validateErrorMessage(String expectedMessage) {

        String actualMessage = response.jsonPath().getString("error");

        assertEquals(actualMessage, expectedMessage);
    }

    // =========================
    // DELAYED RESPONSE
    // =========================

    @Given("I request users with delayed response")
    public void requestDelayedUsers() {

        response = requestSpec()
                .queryParam("delay", 3)
                .when()
                .get("/api/users")
                .then()
                .log().all()
                .extract().response();
    }

    @Then("all returned users should have unique ids")
    public void validateUniqueIds() {

        List<Integer> ids = response.jsonPath().getList("data.id");

        Set<Integer> uniqueIds = new HashSet<>(ids);

        assertEquals(ids.size(), uniqueIds.size(),
                "Duplicate IDs found!");
    }


    @Then("the response should contain created user details")
    public void validateCreatedUser(DataTable table) {

        Map<String, String> data = table.asMap(String.class, String.class);

        System.out.println("Response: " + response.asPrettyString());

        assertEquals(response.getStatusCode(), 201);

        // validate fields
        assertEquals(response.jsonPath().getString("name"), requestName);
        assertEquals(response.jsonPath().getString("job"), requestJob);

        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("createdAt"));
    }

}