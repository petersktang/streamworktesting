package net.elodina.common.stream


import java.util.concurrent.TimeUnit
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.Properties

/**
  * A simple Kafka producer.
  */
class KafkaProducerHelper(bootstrapServers: String) {

  // val props = new Properties()
  
  // props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
  
  // var producer: KafkaProducer[Object, Object] = null
  
    
  // props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
  //   classOf[io.confluent.kafka.serializers.KafkaAvroSerializer])
  // producer = new KafkaProducer[Object, Object](props)
  


   def produce(key: String, value: String, topic: String): Unit = {    
     //producer.send(record).get(3, TimeUnit.SECONDS)
   }
}