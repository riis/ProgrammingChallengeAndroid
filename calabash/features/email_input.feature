@emailinput
Feature: EmailInputActivity
In order to email messages
As a user
I need to connect the app to my email account

Scenario: As a user I want to skip email setup
    Given I am on the Email Input screen
    When I click the Skip button
    And I select the Yes option
    Then I will go to the main screen

Scenario: As a user I enter an invalid email address
    Given I am on the Email Input screen
    When I enter an invalid email address
    And I select the Submit button
    Then I will receive the invalid email error message

Scenario: As a user I enter an invalid password
    Given I am on the Email Input screen
    When I enter a valid email address
    And I select the Submit button
    Then I will receive the invalid password message

Scenario: As a user I want to connect to my email account
    Given I am on the Email Input screen
    When I enter a valid email and password
    And I select the Submit button
    And I select the Submit OK button
    Then I will go to the main screen