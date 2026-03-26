package com.example.dacn.ultil;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JsonConvert implements  AttributeConverter<Map<String, Object>, byte[]> {

	public static final ObjectMapper mapper = new ObjectMapper();

	@Override
	public byte[] convertToDatabaseColumn(Map<String, Object> attribute) {

		if (attribute == null)
			return null;
		try {
			return mapper.writeValueAsBytes(attribute);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> convertToEntityAttribute(byte[] dbData) {
		if (dbData == null)
			return null;
        try {
            return mapper.readValue(dbData, new TypeReference<>() {
});
        } catch (IOException e) {
			throw new RuntimeException(e);
        }
    }

}
