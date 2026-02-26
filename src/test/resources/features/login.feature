Feature: Login functionality
  As a valid user
  I want to login to the application
  So that I can access my dashboard

 @smoke
  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter valid username and password
    And I click on the login button
    Then I should see the dashboard page
