package com.gaboot.backend.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class LazyLoadFilterSerializer<T> extends JsonSerializer<T> {

    private static final ThreadLocal<Set<Object>> serializedObjects = ThreadLocal.withInitial(HashSet::new);

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Set<Object> currentSerializedObjects = serializedObjects.get();

        if (currentSerializedObjects.contains(value)) {
            gen.writeNull(); // Prevent recursive serialization
            return;
        }

        currentSerializedObjects.add(value); // Mark the object as processed

        try {
            if (value instanceof HibernateProxy)
                value = (T) ((HibernateProxy) value).getHibernateLazyInitializer().getImplementation();

            if (value instanceof Collection) {
                Collection<?> collection = (Collection<?>) value;
                if (Hibernate.isInitialized(collection)) {
                    gen.writeStartArray();
                    for (Object item : collection) {
                        gen.writeObject(item);
                    }
                    gen.writeEndArray();
                } else {
                    gen.writeStartArray();
                    gen.writeEndArray();
                }
            } else {
                if (Hibernate.isInitialized(value)) {
                    gen.writeObject(value);
                } else {
                    gen.writeNull();
                }
            }
        } finally {
            currentSerializedObjects.remove(value);
            if (currentSerializedObjects.isEmpty()) {
                serializedObjects.remove();
            }
        }
    }
}