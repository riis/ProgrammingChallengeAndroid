require 'calabash-android/calabash_steps'
require 'pretty_face'

############################################
####################                       #
####################       Email Input     #
####################                       #
############################################

Given(/^I am on the Email Input screen$/) do
  page(EmailInput).await
end

#skip scenario
When(/^I click the Skip button$/) do
  page(EmailInput).skip_button
end

And(/^I select the Yes option$/) do
  page(EmailInput).confirm_skip
end

#invalid email
When(/^I enter an invalid email address$/) do
  page(EmailInput).input_bad_email_address
end

And(/^I select the Submit button$/) do
  page(EmailInput).submit_button
end

Then(/^I will receive the invalid email error message$/) do
  page(EmailInput).view_email_address_error
end

#invalid password
When(/^I enter a valid email address$/) do
  page(EmailInput).input_bad_password
end

Then(/^I will receive the invalid password message$/) do
  page(EmailInput).view_password_error
end

#valid input credentials
When(/^I enter a valid email and password$/) do
  page(EmailInput).input_email_credentials
end

Then(/^I select the Submit OK button$/) do
  page(EmailInput).confirm_submit
end

############################################
####################                       #
####################       Main Screen     #
####################                       #
############################################

#background
Given(/^I am on the Main screen$/) do
  page(EmailInput).await
  page(EmailInput).skip_button
  page(EmailInput).confirm_skip
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
  page(EmailInput).await
  page(EmailInput).skip_button
  page(EmailInput).confirm_skip
  page(MainLanding).await
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
  page(EmailInput).await
  page(EmailInput).skip_button
  page(EmailInput).confirm_skip
  page(MainLanding).await
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

And(/^I click the save list OK button$/) do
  page(CreateContactList).confirm_button
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
  page(EmailInput).await
  page(EmailInput).skip_button
  page(EmailInput).confirm_skip
  page(MainLanding).await
  page(MainLanding).menu_options
  page(MainLanding).import_contacts
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
  page(EmailInput).await
  page(EmailInput).skip_button
  page(EmailInput).confirm_skip
  page(MainLanding).await
  page(MainLanding).menu_options
  page(MainLanding).view_response_message
  page(ViewResponseMessage).await
end

#Cancel Button Scenario
When(/^I click the home button$/) do
  page(ViewResponseMessage).home_button
end