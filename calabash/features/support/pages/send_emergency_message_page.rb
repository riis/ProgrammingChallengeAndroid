require 'calabash-android/abase'

class SendEmergencyMessage < Calabash::ABase

	def trait
		"TextView text:'Send Message'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@no_email_button = "Button id:'noEmailButton'"
	@@message_field = "EditText id:'emergencyMessageField'"
	@@empty_character_count_label = "TextView id:'characterCountLabel' text:'120'"
	@@filled_character_count_label = "TextView id:'characterCountLabel' text:'92'"
	@@cancel_button = "Button id:'cancelEmergencyMessageButton'"

	def assert_character_count
		query(@@empty_character_count_label)
		query(@@message_field, {:setText => 'There has been an emergency!'})		
	end

	def assert_character_count_update
		query(@@filled_character_count_label)
	end

	def no_email_button_exists
		query(@@no_email_button)
	end

	def no_email_button
		touch(query(@@no_email_button))
	end

	def cancel_button
		touch(query(@@cancel_button))
	end

	def assert_send_message_page
		wait_for_elements_exist([trait], :timeout => 5)
		query(trait)
	end
end