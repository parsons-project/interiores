# encoding: utf-8
CAPTURE_TYPES_PATTERN = /^(\D+) & (\d+) - (\d+) & (\S+) - (\S+)/
CAPTURE_MODELS_PATTERN = {
	:begin => /\\begin{tmobles}{(.+)}/,
	:body  => /^(?<name>\D+) &\s{1,2}(?<price>\d+(:?\.\d+)?) & (?<width>\d+) & (?<depth>[^ ]+) & [^ ]+ & (?<color>[^ ]+) & (?<material>[^ ]+)/
}

JAVA_TYPES_PATTERN = "FurnitureType ft = new FurnitureType(\"%s\", new Range(%d, %d), new Range(%s, %s));"
JAVA_MODELS_PATTERN = "ft.addFurnitureModel(new FurnitureModel(\"%s\", new Dimension(%d, %s), %sf, \"%s\",
                \"%s\"));"

SPACE_PADDING = 8

class String
	def remove_non_ascii!
		replacements = { "Ä" => "A", "Å" => "A", "Ö" => "O" }

		replacements.each do |key, value|
			self.gsub!(key, value)
		end

		self
	end

	def translate_color
		translations = { :negro => "black", :marrón => "brown", :rojo => "red", :blanco => "white",
			:azul => "blue", :gris => "gray", :verde => "green", :barnizado => "glazed" }

		gsub(self, translations[self.to_sym]) unless translations[self.to_sym].nil?
	end

	def translate_material
		translations = { :aglomerado => "agglomerate", :pino => "pine", :plástico => "plastic",
			:plastico => "plastic", :acero => "steel", :haya => "beech", :abedul => "birch",
			:marmol => "marble", :porcelana => "porcelain" }

		gsub(self, translations[self.to_sym]) unless translations[self.to_sym].nil?
	end
end

catalog = { :default => [] }

File.open("E1/anexos/tipos.tex").each_line do |line|
	line.scan(CAPTURE_TYPES_PATTERN) do |matches|
		catalog[matches[0]] = []
		catalog[matches[0]] << JAVA_TYPES_PATTERN % [ matches[0], matches[1], matches[2], matches[3], matches[4] ]
	end
end

current_type = :default
File.open("E1/anexos/muebles.tex").each_line do |mline|

	if mline.match(CAPTURE_MODELS_PATTERN[:begin])
		catalog[current_type] << "return ft;"
		current_type = mline.match(CAPTURE_MODELS_PATTERN[:begin])[1]
		catalog[current_type] = [] if catalog[current_type].nil?

	elsif mline.match(CAPTURE_MODELS_PATTERN[:body])
		mline.match(CAPTURE_MODELS_PATTERN[:body]) do |mmatches|
			catalog[current_type] << JAVA_MODELS_PATTERN % [ mmatches[:name].remove_non_ascii!, mmatches[:width],
				mmatches[:depth], mmatches[:price], mmatches[:color].translate_color, mmatches[:material].translate_material ]
		end
	end
end

catalog[current_type] << "return ft;"

catalog.each do |key, values|
	puts key

	values.each { |value| puts " " * SPACE_PADDING + value }
end
