package net.elodina.common.stream

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerRecord}


/**
  * A simple Kafka producer.
  */
class KafkaProducerHelper(bootstrapServers: String) {

   val props = new Properties();
   props.put("bootstrap.servers", bootstrapServers)
   props.put("acks", "all")
   props.put("retries", new Integer(0))
   props.put("batch.size", new Integer(16384))
   props.put("linger.ms", new Integer(1))
   props.put("buffer.memory", new Integer(33554432))
   props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
   props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

   val producer = new KafkaProducer[String, String](props)

   def close(): Unit = {
      producer.close();
   }

   def produce(key: String, value: String, topic: String): Unit = {

      val record = new ProducerRecord[String, String](topic, key, value)
      producer.send(record);

   }
}