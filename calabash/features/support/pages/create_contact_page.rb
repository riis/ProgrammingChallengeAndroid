require 'calabash-android/abase'

class NewContact < Calabash::ABase

	def trait
		"TextView text:'Contact Details'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@first_name_edit_text = "EditText id:'firstNameEditText'"
	@@first_name_error = "Your contact's first name may only contain characters and spaces"
	@@last_name_edit_text = "EditText id:'lastNameEditText'"
	@@email_edit_text = "linearLayout id:'firstContactInfoFragment' EditText id:'contactInfoEditText'"
	@@phone_number_edit_text = "linearLayout id:'secondContactInfoFragment' EditText id:'contactInfoEditText'"
	@@cancel_button = "Button id:'cancelCreateContactButton'"
	@@save_button = "Button id:'saveContactButton'"
	@@confirmation_button = "Button id:'button1'"

	def assert_contact_page
		wait_for_elements_exist([trait], :timeout => 9)
	end

	def input_contact_value
		wait_for_elements_exist([@@first_name_edit_text], :timeout => 3)
		query(@@first_name_edit_text, {:setText => 'Bob'})
		query(@@last_name_edit_text, {:setText => 'Jones'})
		query(@@email_edit_text, {:setText => 'bj@example.com'})
		query(@@phone_number_edit_text, {:setText => '1234567890'})
	end

	def input_bad_first_name
		wait_for_elements_exist([@@first_name_edit_text], :timeout => 3)
		query(@@first_name_edit_text, {:setText => 'Bad name'})
	end

	def view_first_name_error
		query(@@first_name_error)
	end

	def save_button
		touch(query(@@save_button))
	end

	def cancel_button
		touch(query(@@cancel_button))
	end

	def confirm_create
		wait_for_elements_exist([@@confirmation_button], :timeout => 3)
		touch(query(@@confirmation_button))
	end
end