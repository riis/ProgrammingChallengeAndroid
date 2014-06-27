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

	def assert_view_response_page
		wait_for_elements_exist([trait], :timeout =>9)
	end
end