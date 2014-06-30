require 'calabash-android/abase'

class ViewResponseMessage < Calabash::ABase

	def trait
		"TextView text:'View Response Messages'"
	end

	def await(opts={})
		wait_for_elements_exist([trait], :timeout => 5)
		self
	end

	#elements
	@@home_button = "Button id:'returnToMainScreenButton'"

	def assert_view_response_page
		wait_for_elements_exist([trait], :timeout =>9)
	end

	def home_button
		touch(query(@@home_button))
	end
end