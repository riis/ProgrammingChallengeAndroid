@importcontact
Feature: ViewResponseMessagesActivity
In order to use contacts already on my device
As a user
I want to import contacts

Background:
     Given I am on the Import Contacts page

Scenario: As a user I want to return to the main screen
    When I click the cancel import button
    Then I will go to the main screen