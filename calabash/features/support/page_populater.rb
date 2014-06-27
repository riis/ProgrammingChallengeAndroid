module PagePopulator
	include DataMagic

	def populate_page_name_with(data_file)
		data = data_for data_file
		data.each do |key, value|
			query("EditText id:'#{key}'", {:setText => values})
		end
	end

	def populate_page_fragment_with(data_file)
		data = data_for data_file
		data.each do |key, value|
			query("linearLayout id:'#{key}' EditText id:'contactInfoEditText'", {:setText => value})
		end
	end
end