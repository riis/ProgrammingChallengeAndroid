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
	@@create_contact_page = "* id:'first_name_editText'"

	def menu_options
		touch(query(@@more_option))
	end

	def create_contact
		touch(query(@@create_contact))
	end

	def assert_contact_page
		wait_for_elements_exist([@@create_contact_page], :timeout =>9)
	end
end