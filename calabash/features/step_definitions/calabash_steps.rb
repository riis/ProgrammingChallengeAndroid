require 'calabash-android/calabash_steps'

Given(/^What I am on the main screen$/) do
  page(MainLanding).await
end

When(/^I open the options menu$/) do
  page(MainLanding).menu_options
end

When(/^I press Create Contact$/) do
  page(MainLanding).create_contact
end

Then(/^I will go into the Create Contact screen$/) do
  page(MainLanding).assert_contact_page
end
