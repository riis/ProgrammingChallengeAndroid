require 'calabash-android/calabash_steps'

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
  page(MainLanding).assert_contact_page
end

When(/^I select the Create Contact List option$/) do
	page(MainLanding).create_contact_list
end

Then(/^I will be taken to the Create Contact List screen$/) do
	page(MainLanding).assert_contact_list_page
end

When(/^I select the Import Contacts option$/) do
  page(MainLanding).import_contacts
end

Then(/^I will be taken to the Import Contacts screen$/) do
	page(MainLanding).assert_import_contact_page
end

When(/^I select the View Response Messages option$/) do
  page(MainLanding).view_response_message
end

Then(/^I will be taken to the View Response Messages screen$/) do
  page(MainLanding).assert_view_response_page
end