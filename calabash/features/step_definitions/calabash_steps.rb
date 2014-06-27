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

Then(/^I will be taken to the Create Contact screen$/) do
  page(MainLanding).assert_contact_page
end