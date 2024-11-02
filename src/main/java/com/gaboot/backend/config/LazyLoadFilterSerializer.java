package com.gaboot.backend.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LazyLoadFilterSerializer<T> extends JsonSerializer<T> {

    private static final Set<Object> serializedObjects = new HashSet<>();

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        System.out.println("CLASS: "+Hibernate.getClassLazy(value));
        System.out.println("CLASS: "+(value instanceof Collection<?>));
        if (serializedObjects.contains(value)) {
            gen.writeNull(); // Prevent recursive serialization
            return;
        }

        serializedObjects.add(value); // Mark the object as processed

        if (value instanceof Collection) {
            Collection<?> collection = (Collection<?>) value;
            if (Hibernate.isInitialized(collection)) {
                gen.writeStartArray();
                for (Object item : collection) {
                    serialize((T) item, gen, serializers); // Recursively serialize items
                }
                gen.writeEndArray();
            } else {
                gen.writeStartArray(); // Write an empty array if the collection is uninitialized
                gen.writeEndArray();
            }
        } else {
            if (Hibernate.isInitialized(value)) {
                gen.writeObject(value); // Serialize if initialized
            } else {
                gen.writeNull(); // Avoid initializing and return null
            }
        }

        serializedObjects.remove(value); // Cleanup after serialization
    }
}