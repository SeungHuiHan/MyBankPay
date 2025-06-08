package com.bankpay.loggingconsumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class LoggingConsumer {
    //private final KafkaConsumer<String, String> consumer;
    @Bean
    public KafkaConsumer<String, String> initConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers
            , @Value("${logging.topic}")String topic){
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "my-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
        return kafkaConsumer;
    }


//    public LoggingConsumer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
//                           @Value("${logging.topic}")String topic) {
//
//        Properties props = new Properties();
//
//        props.put("bootstrap.servers", bootstrapServers);
//
//        // consumer group
//        props.put("group.id", "my-group");
//
//        //deserializer를 String형태로
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        this.consumer = new KafkaConsumer<>(props);
//
//        consumer.subscribe(Collections.singletonList(topic));
//        Thread consumerThread = new Thread(() -> {
//            try {
//                while (true) {
//                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
//                    for (ConsumerRecord<String, String> record : records) {
//                        // print to Stdout 컨슘한 value출력
//                        System.out.println("Received message: " + record.value());
//                    }
//                }
//            } finally {
//                consumer.close();
//            }
//        });
//        consumerThread.start();
//    }
}
