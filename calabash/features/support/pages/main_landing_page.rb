require 'calabash-android/abase'

class MainLanding < Calabash::ABase

	def trait
		"* contentDescription:'More options'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@more_option = "* contentDescription:'More options'"
	@@create_contact = "* text:'Create Contact'"
	@@create_contact_list = "* text:'Create Contact List'"
	@@import_contacts = "* text:'Import Contacts'"
	@@view_response_message = "* text:'View Response Messages'"
	@@create_contact_page = "TextView text:'Create Contact'"
	@@create_contact_list_page = "TextView text:'Create Contact List'"
	@@import_contacts_page = "TextView text:'Import Contacts'"
	@@view_response_message_page = "TextView text:'View Response Messages'"

	def menu_options
		touch(query(@@more_option))
	end

	def create_contact
		touch(query(@@create_contact))
	end

	def create_contact_list
		touch(query(@@create_contact_list))
	end

	def import_contacts
		touch(query(@@import_contacts))
	end

	def view_response_message
		touch(query(@@view_response_message))
	end

	def assert_contact_page
		wait_for_elements_exist([@@create_contact_page], :timeout =>9)
	end

	def assert_contact_list_page
		wait_for_elements_exist([@@create_contact_list_page], :timeout =>9)
	end

	def assert_import_contact_page
		wait_for_elements_exist([@@import_contacts_page], :timeout =>9)
	end

	def assert_view_response_page
		wait_for_elements_exist([@@view_response_message_page], :timeout =>9)
	end
end