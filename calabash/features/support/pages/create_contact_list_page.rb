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

	def assert_contact_list_page
		wait_for_elements_exist([trait], :timeout =>9)
	end
end