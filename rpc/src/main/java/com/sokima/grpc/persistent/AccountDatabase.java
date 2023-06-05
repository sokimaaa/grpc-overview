package com.sokima.grpc.persistent;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Emulate Database
 */
public class AccountDatabase {

    private AccountDatabase() {

    }

    private static final Map<Integer, Integer> MAP = IntStream.rangeClosed(1, 50)
            .boxed()
            .collect(Collectors.toMap(
                    Function.identity(),
                    v -> v * 50
            ));

    public static int getBalance(int accountId) {
        return MAP.get(accountId);
    }

    public static Integer addBalanceAndReturn(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k, v) -> v + amount);
    }

    public static Integer deductBalanceAndReturn(int accountId, int amount) {
        return MAP.computeIfPresent(accountId, (k, v) -> v - amount);
    }

    public static void printDatabase() {
        System.out.println(MAP);
    }
}
