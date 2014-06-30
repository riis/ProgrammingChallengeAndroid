@createcontactlist
Feature: CreateContactActivity
In order to properly organize the contacts
As a user
I want to create a contact list

Background:
     Given I am on the Create Contact List page

Scenario: Complete Empty Contact List screen
    When I enter a valid list name
    And I click the save list button
    Then I will go to the main screen

Scenario: List Name Error
    When I enter an improper list name
    And I click the save list button
    Then I will receive the list name error message

Scenario: As a user I want to return to the main screen
    When I click the cancel list create button
    Then I will go to the main screen