@createcontact
Feature: NewContactActivity
In order to properly alert the right people
As a user
I want to create a contact

Background:
     Given I am on the Create Contact page

Scenario: Complete Contact screen
    When I complete the Create Contact form
    And I click the save button
    And I click the OK button
    Then I will go to the main screen

Scenario: First Name Error
    When I enter an improper first name
    And I click the save button
    Then I will receive the first name error message

Scenario: As a user I want to return to the main screen
    When I click the cancel button
    Then I will go to the main screen