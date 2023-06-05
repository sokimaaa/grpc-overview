package com.sokima.grpc.utils;

import com.sokima.grpc.model.Person;

import java.util.Objects;

/**
 * There is no way to override Person#equals method in proto files directly,
 * So it's recommended by community to create utility class for such purposes.
 */
public final class PersonUtil {

    private PersonUtil() {

    }

    public static boolean equals(Person person1, Person person2) {
        if (person1 == person2) {
            return true;
        }

        if (Objects.isNull(person1) || Objects.isNull(person2)) {
            return false;
        }

        return person1.getAge().getValue() == person2.getAge().getValue() &&
                person1.getName().equalsIgnoreCase(person2.getName());
    }
}
