package com.bankpay.money.moneyservice.adapter.out.kafka;

import com.bankpay.common.RechargingMoneyTask;
import com.bankpay.money.moneyservice.application.port.out.SendRechargingMoneyTaskPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaskProducer implements SendRechargingMoneyTaskPort {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    public TaskProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
                        @Value("${task.topic}")String topic) {

        Properties props=new Properties();
        props.put("bootstrap.servers", bootstrapServers); //Kafka cluster내의 broker에ek producer를 하게 됨
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //직렬화
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //역직렬화

        this.producer = new KafkaProducer<>(props);
        this.topic=topic;

    }

    @Override
    public void sendRechargingMoneyTaskPort(RechargingMoneyTask task) {
       this.sendMessage(task.getTaskID(),task);
    }


    //Kafka Cluster [key,value] Produce
    public void sendMessage(String key,RechargingMoneyTask value) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonStringToProduce;

        try {
            jsonStringToProduce= mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key,jsonStringToProduce);
        producer.send(record,(metadata, exception) -> {
            if (exception != null) {

            }else{
                exception.printStackTrace();
            }
        });
    }
}
