package net.elodina.stream.common



import java.util
import java.util.Properties


import kafka.consumer.{ConsumerConfig, KafkaStream, Whitelist}
import kafka.utils.VerifiableProperties

import scala.collection.JavaConverters._

class KafkaConsumerHelper(numOfStream: Int,
                        zookeeperConnect: String,
                        topicWhitelist: String,
                        groupId: String,
                        schemaRegistryUrl: String,
                        autoCommitInterval: Long) {

  // val props = new Properties()
  // props.put("zookeeper.connect", zookeeperConnect)
  // props.put("group.id", groupId)
  // props.put("auto.commit.enable", "true")
  // props.put("auto.commit.interval.ms", autoCommitInterval.toString)

  // private val topicCountMap = new util.HashMap[String, Integer]()
  // topicCountMap.put("topic1", new Integer(1))

  
  // private val consumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(props))

  // private val consumerStreams: util.List[KafkaStream[AnyRef, AnyRef]] =
  //   consumer.createMessageStreamsByFilter(new Whitelist(topicWhitelist), numOfStream, keyDecoder, valueDecoder)

  // def iterators() = consumerStreams.asScala.map(_.iterator()).toSeq

  // def shutdown() = consumer.shutdown()
}