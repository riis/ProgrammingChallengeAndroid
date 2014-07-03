@moreoptions
Feature: DisasterAppActivity
In order to properly use the system
As a user
I want navigate through the system

Background:
     Given I am on the Main screen

Scenario: As a user I want to create a contact
    When I open the options menu list
    And I select the Create Contact option
    Then I will go into the Create Contact screen

Scenario: As a user I want to create a contact list
    When I open the options menu list
    And I select the Create Contact List option
    Then I will be taken to the Create Contact List screen

Scenario: As a user I want to import an existing contact
    When I open the options menu list
    And I select the Import Contacts option
    Then I will be taken to the Import Contacts screen

Scenario: As a user I want to view responses to alerts
    When I open the options menu list
    And I select the View Response Messages option
    Then I will be taken to the View Response Messages screen
