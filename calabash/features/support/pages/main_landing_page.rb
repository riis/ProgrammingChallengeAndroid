require 'calabash-android/abase'

class MainLanding < Calabash::ABase

	def trait
		"TextView text:'Emergency Messaging'"
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
	@@everyone_list_row = "TextView text:'Everyone'"
	@@no_contacts_in_list_label = "TextView text:'There are no contacts in this list! Please add a contact!'"
	@@single_contact_label = "TextView text:'Bob Jones'"
	@@send_message_button = "TextView text:'Everyone' sibling Button id:'sendMessageContactListButton'"

	def send_message_button
		touch(query(@@send_message_button))
	end

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

	def expand_everyone_list
		touch(query(@@everyone_list_row))
	end

	def assert_empty_list
		query(@@no_contacts_in_list_label)
	end

	def assert_single_contact
		query(@@single_contact_label)
	end

	def assert_main_page
		wait_for_elements_exist([trait], :timeout => 5)
		query(trait)
	end
end