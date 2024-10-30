package com.gaboot.backend.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.Hibernate;

import java.io.IOException;

public class LazyLoadFilterSerializer<T> extends JsonSerializer<T> {
    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (Hibernate.isInitialized(value)) {
            gen.writeObject(value); // Serialize if initialized
        } else {
            gen.writeNull(); // Avoid initializing and return null
        }
    }
}