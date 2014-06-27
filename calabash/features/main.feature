@moreoptions
Feature: DisasterAppActivity
In order to properly use the system
As a user
I want navigate through the system

Scenario: As a user I want to create a contact
    Given I am on the Main screen
    When I open the options menu list
    And I select the Create Contact option
    Then I will go into the Create Contact screen

Scenario: As a user I want to create a contact list
    Given I am on the Main screen
    When I open the options menu list
    And I select the Create Contact List option
    Then I will be taken to the Create Contact List screen

Scenario: As a user I want to import an existing contact
    Given I am on the Main screen
    When I open the options menu list
    And I select the Import Contacts option
    Then I will be taken to the Import Contacts screen

Scenario: As a user I want to view responses to alerts
    Given I am on the Main screen
    When I open the options menu list
    And I select the View Response Messages option
    Then I will be taken to the View Response Messages screen
