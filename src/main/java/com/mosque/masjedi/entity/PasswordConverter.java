package com.mosque.masjedi.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Base64;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    private static final String SECRET_KEY = "mySecretKey"; // Replace with a secure key

    @Override
    public String convertToDatabaseColumn(String attribute) {
        // Simple Base64 encoding for demonstration purposes
        return Base64.getEncoder().encodeToString((attribute + SECRET_KEY).getBytes());
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // Simple Base64 decoding for demonstration purposes
        String decoded = new String(Base64.getDecoder().decode(dbData));
        return decoded.replace(SECRET_KEY, "");
    }
}
