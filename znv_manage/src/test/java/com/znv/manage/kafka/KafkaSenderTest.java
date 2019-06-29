package com.znv.manage.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaSenderTest {

    private static final String TEST_TOPIC = "test";

    @Autowired
    KafkaSender kafkaSender;

    @Test
    public void send() {
        kafkaSender.send(TEST_TOPIC, "test payload from KafkaSenderTest send().");
    }
}