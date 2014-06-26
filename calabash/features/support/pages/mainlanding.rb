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
	@@create_contact = "* Text:'Create Contact'"
	@@create_contact_list = "* Text: Create Contact List'"
	@@import_contacts = "* Text:'Import Contacts'"
	@@view_response = "* Text:'View Response Messages'"
	@@login_page = "* text:'Create Contact'"

def menu_options
	touch(query(@@more_option))
end

def create_contact
	touch(query(@@create_contact))
end

def assert_contact_page
	wait_for_elements_exist([@@login_page], :timeout =>9)
end






end

