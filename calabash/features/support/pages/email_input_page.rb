require 'calabash-android/abase'

class EmailInput < Calabash::ABase

	def trait
		"EditText id:'emailAddressInput'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@email_address_edit_text = "EditText id:'emailAddressInput'"
	@@email_address_error_message = "TextView text:'Please enter a valid email address!'"
	@@password_edit_text = "EditText id:'passwordInput'"
	@@password_error_message = "TextView text:'Please enter your password!'"
	@@skip_button = "Button id:'skipButton'"
	@@submit_button = "Button id:'submitEmailInfoButton'"
	@@confirmation_button = "Button id:'button1'"

	def input_email_credentials
		wait_for_elements_exist([@@email_address_edit_text], :timeout => 3)
		query(@@email_address_edit_text, {:setText => 'test@test.com'})
		query(@@password_edit_text, {:setText => 'password'})
	end

	def input_bad_email_address
		wait_for_elements_exist([@@email_address_edit_text], :timeout => 3)
		query(@@email_address_edit_text, {:setText => 'invalid email@address'})
	end

	def input_bad_password
		wait_for_elements_exist([@@email_address_edit_text], :timeout => 3)
		query(@@email_address_edit_text, {:setText => 'test@test.com'})
	end

	def view_email_address_error
		query(@@email_address_error_message)
	end

	def view_password_error
		query(@@password_error_message)
	end

	def skip_button
		touch(query(@@skip_button))
	end

	def submit_button
		touch(query(@@submit_button))
	end

	def confirm_skip
		wait_for_elements_exist([@@confirmation_button], :timeout => 3)
		touch(query(@@confirmation_button))
	end

	def confirm_submit
		wait_for_elements_exist([@@confirmation_button], :timeout => 3)
		touch(query(@@confirmation_button))
	end

	def assert_email_input_page
		wait_for_elements_exist([trait], :timeout => 5)
		query(trait)
	end
end