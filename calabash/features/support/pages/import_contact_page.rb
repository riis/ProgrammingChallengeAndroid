require 'calabash-android/abase'

class ImportContact < Calabash::ABase

	def trait
		"TextView text:'Import Contacts'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@cancel_button = "Button id:'cancelImportContactsButton'"

	def assert_import_contact_page
		wait_for_elements_exist([trait], :timeout =>9)
	end

	def assert_main_page
		wait_for_elements_exist([@@main_screen], :timeout =>9)
	end

	def cancel_button
		touch(query(@@cancel_button))
	end
end