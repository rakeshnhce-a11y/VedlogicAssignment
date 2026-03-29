@API
Feature: API test using ReqRes

  Scenario: Verify users list across pages
    Given users requested for page 1
    When I request users for page 2
    Then the number of users returned should match the per_page count


  Scenario: Verify single user details
    Given I request user with id 3
    Then the response should contain user details
      | first_name | email               |
      | Emma       | emma.wong@reqres.in |


  Scenario: Verify user not found
    Given I request user with id 55
    Then the response status should be 404


  Scenario Outline: Create a new user
    Given I create a user with "<Name>" and "<Job>"
    Then the response should contain created user details
      | name | job |

    Examples:
      | Name  | Job     |
      | Peter | Manager |
      | Liza  | Sales   |


  Scenario: Successful login
    Given I send login request with the following data
      | Email              | Password   |
      | eve.holt@reqres.in | cityslicka |
    Then the response status should be 200


  Scenario: Unsuccessful login
    Given I send login request with the following data
      | Email              | Password |
      | eve.holt@reqres.in |          |
    Then the response status should be 400
    And the response error message should be "Missing password"


  Scenario: Verify delayed user response
    Given I request users with delayed response
    Then all returned users should have unique ids