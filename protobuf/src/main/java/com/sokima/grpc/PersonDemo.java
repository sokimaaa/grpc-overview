package com.sokima.grpc;

import com.google.protobuf.Int32Value;
import com.sokima.grpc.model.Person;
import com.sokima.grpc.utils.PersonSerializeUtil;
import com.sokima.grpc.utils.PersonUtil;

public class PersonDemo {

    public static void main(String[] args) {
        Person mike = Person.newBuilder()
                .setName("Mike")
                .setAge(Int32Value.newBuilder().setValue(19).build())
                .build();

        Person rose = Person.newBuilder()
                .setName("Rose")
                .setAge(Int32Value.of(22))
                .build();

        final boolean isEquals = PersonUtil.equals(mike, rose);

        PersonSerializeUtil.serialize(rose);

        Person deserialize = PersonSerializeUtil.deserialize();

        System.out.println(deserialize);
    }
}
