package com.erwin.healthcare.processor

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import org.apache.kafka.clients.producer.ProducerRecord
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


/**
 * Created by erwinalberto on 1/22/18.
 */
@Component
class OpenPaymentProducer {

    @Value("\${erwindev.bootstrap.servers}") lateinit var bootStrapServers: String
    @Value("\${erwindev.kafka.client.id}") lateinit var clientIdConfig: String
    @Value("\${erwindev.kafka.open.payment.topic}") lateinit var openPaymentTopic: String

    var kafkaProducer: Producer<String, String>? = null

    @PostConstruct
    fun init(){
        kafkaProducer = createProducer()
    }

    private fun createProducer(): Producer<String, String> {
        val props = Properties()
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers)
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientIdConfig)
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer::class.java.name)
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer::class.java.name)
        return KafkaProducer(props)
    }

    @Throws(Exception::class)
    fun runProducer(id: Int, data: String) {
        try {
            val record = ProducerRecord(openPaymentTopic, id.toString(), data)
            val metadata = kafkaProducer?.send(record)?.get()
        } finally {
            kafkaProducer?.flush()
        }
    }

    @PreDestroy
    fun cleanUp(){
        kafkaProducer?.close()
    }
}