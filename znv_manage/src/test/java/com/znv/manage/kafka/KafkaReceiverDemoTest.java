package com.znv.manage.kafka;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KafkaReceiverDemoTest {

    private static final String TEST_TOPIC = "test";

    @Autowired
    KafkaSender kafkaSender;

    @Autowired
    KafkaReceiverDemo kafkaReceiverDemo;

    @Test
    public void listen() throws InterruptedException {
        kafkaSender.send(TEST_TOPIC, "test payload from KafkaReceiverDemoTest listen().");
        kafkaReceiverDemo.getLatch().await(10, TimeUnit.SECONDS);
        assertThat(kafkaReceiverDemo.getLatch().getCount()).isEqualTo(0);
    }
}