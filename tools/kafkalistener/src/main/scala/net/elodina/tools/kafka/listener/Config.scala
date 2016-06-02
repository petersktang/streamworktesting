package net.elodina.tools.kafka.listener

import java.io.{File, FileInputStream, InputStream}
import java.util.Properties

import org.slf4j.LoggerFactory
import scopt.OptionParser

import scala.util.Try

object Config {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def apply(file: File, overrides: Map[String, String]): Config = {
    val props = loadConfigFile(file)

    overrides.foreach {
      case (k, v) =>
        logger.info(s"Configuration setting is overridden: $k=$v")
        props.setProperty(k, v)
    }

    new Config(props)
  }

  def apply(file:File, args:Seq[String]): Config = {
    apply(file, cliParser.parse(args, Map[String, String]()).getOrElse{
      logger.warn("Incorrect command line arguments. Ignoring")
      Map[String, String]()
    })
  }

  private def loadConfigFile(cfg: File): Properties = {
    val props = new Properties()

    var is: InputStream = null
    try {
      is = new FileInputStream(cfg)
      props.load(is)
    } finally {
      Try(if (is != null) is.close())
    }

    props
  }

  val cliParser = new OptionParser[Map[String, String]] ("kafka-listner") {

    opt[String] ("kafka.bootstrap.servers").optional().text("Kafka bootstrap servers").action {
      (value, config) =>
        config.updated ("kafka.bootstrap.servers", value)
    }

    opt[String] ("kafka.topic").optional().text("Kafka topic").action {
      (value, config) =>
        config.updated ("kafka.topic", value)
    }

    opt[String] ("group.id").optional().text("Kafka group id").action {
      (value, config) =>
        config.updated ("group.id", value)
    }

    opt[String] ("enable.auto.commit").optional().text("Kafka auto commit for group's offset").action {
      (value, config) =>
        config.updated ("enable.auto.commit", value)
    }

    opt[String] ("listener.buffer.max.size").optional().text("Maximium number of items for internal buffer of topics read from kafka").action {
      (value, config) =>
        config.updated ("listener.buffer.max.size", value)
    }

    opt[String] ("listener.read.wait.ms").optional().text("Wait time between dequeuing an item from the topic item internal buffer (for display)").action {
      (value, config) =>
        config.updated ("listener.read.wait.ms", value)
    }

    opt[String] ("auto.offset.reset").optional().text("Tells the kafka consumer to read from the beginning of the topic partition").action {
      (value, config) =>
        config.updated ("auto.offset.reset", value)
    }


  }
}


class Config(props: Properties) {

  val KafkaBootstrapServers = props.getProperty("kafka.bootstrap.servers")
  val KafkaTopic = props.getProperty("kafka.topic")
  val GroupId = props.getProperty("group.id")
  val EnableAutoCommit = props.getProperty("enable.auto.commit")
  val ListenerBufferMaxSize = props.getProperty("listener.buffer.max.size")
  val ListenerReadWaitMs = props.getProperty("listener.read.wait.ms")
  val AutoOffsetReset = props.getProperty("auto.offset.reset")

}

