@sendmessage
Feature: SendEmergencyMessageScreen
In order to alert contacts of an emergency
As a user
I want to send an emergency message

Background:
    Given I am on the Send Emergency Message screen

Scenario: Setup an email address
    When An email address is not setup
    And I click the Setup Email button
    Then I will go to the Email Input screen

Scenario: The character count should change as the message is input
    When I enter a message
    Then The character count updates

Scenario: As a user I want to return to the main screen
    When I click the cancel send message button
    Then I will go to the main screen