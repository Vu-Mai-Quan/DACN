package com.example.dacn.config.annotations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Base64;

public class Base64ToBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();

        if (value == null || value.isEmpty()) {
            return false;
        }

        if (value.startsWith("base64:type16:")) {
            String base64Value = value.substring("base64:type16:".length());
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(base64Value);
                if (decodedBytes.length > 0) {
                    return decodedBytes[0] == 1;
                }
            } catch (IllegalArgumentException e) {
                throw new IOException("Invalid Base64 string", e);
            }
        }
        return false;
    }
}