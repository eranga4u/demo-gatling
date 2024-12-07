package com.eranga.feeder;

import com.github.javafaker.Faker;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class Feeders {

    public static final Iterator<Map<String, Object>> FEED_DATA = setupTestFeedData();

    private static Iterator<Map<String, Object>> setupTestFeedData() {
        Faker faker = new Faker();
        Iterator<Map<String, Object>> iterator;
        iterator = Stream.generate(() -> {
                Map<String, Object> stringObjectMap = new HashMap<>();
                stringObjectMap.put("empName", faker.name()
                    .fullName());
                return stringObjectMap;
            })
            .iterator();
        return iterator;
    }

}
