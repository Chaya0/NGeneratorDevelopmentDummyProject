package com.lms.utils;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.data.domain.Sort;
import java.io.IOException;

public class CustomSortSerializer extends JsonSerializer<Sort> {

	@Override
	public void serialize(Sort value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartArray();
		value.iterator().forEachRemaining(v -> {
			try {
				gen.writeObject(v);
			} catch (IOException e) {
				throw new RuntimeException("Couldn't serialize object " + v);
			}
		});
		gen.writeEndArray();
	}

	@Override
	public Class<Sort> handledType() {
		return Sort.class;
	}
}
