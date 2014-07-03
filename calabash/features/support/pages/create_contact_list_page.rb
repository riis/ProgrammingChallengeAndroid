require 'calabash-android/abase'

class CreateContactList < Calabash::ABase

	def trait
		"TextView text:'Create Contact List'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@contact_list_name_edit_text = "EditText id:'contactListNameText'"
	@@contact_list_name_error = "TextView text:'Please enter a name for the contact list!'"
	@@contact_list_save_button = "Button id:'saveCreateContactListSaveButton'"
	@@contact_list_cancel_button = "Button id:'cancelCreateContactListButton'"
	@@confirmation_button = "Button id:'button1'"

	def assert_contact_list_page
		wait_for_elements_exist([trait], :timeout => 9)
	end

	def input_empty_list
		wait_for_elements_exist([trait], :timeout => 9)
		query(@@contact_list_name_edit_text, {:setText => 'Friends'})
	end

	def input_bad_list_name
		wait_for_elements_exist([trait], :timeout => 9)
		query(@@contact_list_name_edit_text, {:setText => ''})
	end

	def view_list_name_error
		query(@@contact_list_name_error)
	end

	def save_button
		touch(query(@@contact_list_save_button))
	end

	def cancel_button
		touch(query(@@contact_list_cancel_button))
	end

	def confirm_button
		wait_for_elements_exist([@@confirmation_button], :timeout => 3)
		touch(query(@@confirmation_button))
	end
end