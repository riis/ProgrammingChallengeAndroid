module PagePopulator
	include CompleteForms
	include DataMagic

	def populate_page_with(data_file)
		data = data_for data_file
		data.each do |key, value|
				query("EditText id:'#{key}'", {:setText => value})
		end
	end


	def populate_contact_with(data_file)
			data = data_for data_file
			data.each do |key, value|
					query("LinearLayout id:'#{key}' editText id:'contactInfoEditText:'", {:setText => value})
			end
		end


		def select_course_with(data)
			data.each do |key, value|
				element = "TextView text:'#{key}'"
			end
		end
end
