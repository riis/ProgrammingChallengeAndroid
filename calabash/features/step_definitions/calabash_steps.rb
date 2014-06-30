require 'calabash-android/calabash_steps'
require 'pretty_face'

############################################
####################                       #
####################       Main Screen     #
####################                       #
############################################

Given(/^I am on the Main screen$/) do
  page(MainLanding).await
end

When(/^I open the options menu list$/) do
  page(MainLanding).menu_options
end

When(/^I select the Create Contact option$/) do
  page(MainLanding).create_contact
end

Then(/^I will go into the Create Contact screen$/) do
  page(NewContact).assert_contact_page
end

When(/^I select the Create Contact List option$/) do
	page(MainLanding).create_contact_list
end

Then(/^I will be taken to the Create Contact List screen$/) do
	page(CreateContactList).assert_contact_list_page
end

When(/^I select the Import Contacts option$/) do
  page(MainLanding).import_contacts
end

Then(/^I will be taken to the Import Contacts screen$/) do
	page(ImportContact).assert_import_contact_page
end

When(/^I select the View Response Messages option$/) do
  page(MainLanding).view_response_message
end

Then(/^I will be taken to the View Response Messages screen$/) do
  page(ViewResponseMessage).assert_view_response_page
end

Then(/^I will go to the main screen$/) do
  page(MainLanding).assert_main_page
end

############################################
####################                       #
####################     Create Contact    #
####################                       #
############################################

#background
Given(/^I am on the Create Contact page$/) do
  page(MainLanding).menu_options
  page(MainLanding).create_contact
  page(NewContact).await
end

#Save Properly Scenario
When(/^I complete the Create Contact form$/) do
  page(NewContact).input_contact_value
end

#Improper First Name Scenario
When(/^I enter an improper first name$/) do
  page(NewContact).input_bad_first_name
end

Then(/^I will receive the first name error message$/) do
  page(NewContact).view_first_name_error
end

And(/^I click the save button$/) do
  page(NewContact).save_button
end

And(/^I click the OK button$/) do
  page(NewContact).confirm_create
end

#Cancel Button Scenario
When(/^I click the cancel button$/) do
  page(NewContact).cancel_button
end

############################################
####################                       #
####################  Create Contact List  #
####################                       #
############################################

#background
Given(/^I am on the Create Contact List page$/) do
  page(MainLanding).menu_options
  page(MainLanding).create_contact_list
  page(CreateContactList).await
end

#Save Properly Scenario
When(/^I enter a valid list name$/) do
  page(CreateContactList).input_empty_list
end

#Improper List Name Scenario
When(/^I enter an improper list name$/) do
  page(CreateContactList).input_bad_list_name
end

Then(/^I will receive the list name error message$/) do
  page(CreateContactList).view_list_name_error
end

And(/^I click the save list button$/) do
  page(CreateContactList).save_button
end

#Cancel Button Scenario
When(/^I click the cancel list create button$/) do
  page(CreateContactList).cancel_button
end

############################################
####################                       #
####################    Import Contacts    #
####################                       #
############################################

#background
Given(/^I am on the Import Contacts page$/) do
  page(MainLanding).menu_options
  page(MainLanding).create_contact_list
  page(ImportContact).await
end

#Cancel Button Scenario
When(/^I click the cancel import button$/) do
  page(ImportContact).cancel_button
end

############################################
####################                       #
#################### View Response Message #
####################                       #
############################################

#background
Given(/^I am on the View Response Messages page$/) do
  page(MainLanding).menu_options
  page(MainLanding).create_contact_list
  page(ViewResponseMessage).await
end

#Cancel Button Scenario
When(/^I click the home button$/) do
  page(ViewResponseMessage).home_button
end