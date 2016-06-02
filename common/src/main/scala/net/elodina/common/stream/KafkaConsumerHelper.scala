package net.elodina.stream.common



import java.util
import java.util.Properties
import java.util.concurrent.BlockingQueue

import org.apache.kafka.clients.consumer.{ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.slf4j.LoggerFactory
import collection.JavaConversions._



class KafkaConsumerHelper(bootStrapperServers: String,
                          groupid: String,
                          enableAutoCommit: Boolean,
                          autoOffsetReset: String,
                          topics: util.List[String],
                          messageQueue: BlockingQueue[ConsumerRecord[String,String]])
                          extends Runnable {


  val log = LoggerFactory.getLogger(this.getClass)

  var shutdownFlag = false


  val props = new Properties()
  props.put("bootstrap.servers", bootStrapperServers)
  props.put("group.id", groupid)
  props.put("enable.auto.commit", enableAutoCommit.toString)
  props.put("auto.offset.reset", autoOffsetReset)
  props.put("auto.commit.interval.ms", "1000")
  props.put("session.timeout.ms", "30000")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(topics)

  def shutdown() {
    shutdownFlag = true
  }

  override def run(): Unit = {

    while (!shutdownFlag) {

      val records: ConsumerRecords[String, String] = consumer.poll(100)

      for (record: ConsumerRecord[String, String] <- records) {

        messageQueue.put(record)

        log.debug(s"offset = ${record.offset}%d, key = ${record.key}%s, value = ${record.value}%s")
      }

    }

  }

}