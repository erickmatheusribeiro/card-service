package com.spring.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TestUtils {

    protected final ObjectMapper objectMapper;

    protected Clock clock;

    public TestUtils() {
        this.clock = Clock.fixed(
                Instant.parse("2024-07-01T08:40:00.000-03:00"),
                ZoneId.of("America/Sao_Paulo")
        );

        this.objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    protected File getMock(String path) {
        return new File(path);
    }

    protected void assertJsonEquals(String expected, String actual) throws JSONException {
        JSONAssert.assertEquals(
                expected,
                actual,
                false);
    }
}
