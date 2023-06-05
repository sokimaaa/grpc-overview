package com.sokima.grpc.utils;

import com.sokima.grpc.model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The way to serialize\deserialize proto files.
 */
public final class PersonSerializeUtil {

    private static final Path path;

    static {
        path = Paths.get("serialized");
    }

    private PersonSerializeUtil() {

    }

    public static void serialize(Person person) {
        try {
            byte[] bytes = person.toByteArray();
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new PersonSerializationException(e);
        }
    }

    public static Person deserialize() {
        return deserialize(path);
    }

    public static Person deserialize(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            return Person.parseFrom(bytes);
        } catch (IOException e) {
            throw new PersonSerializationException(e);
        }
    }

    private static class PersonSerializationException extends RuntimeException {
        public PersonSerializationException(IOException e) {
            super(e);
        }
    }
}
