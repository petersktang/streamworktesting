package net.elodina.examples.oneusagov.stream

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

  val cliParser = new OptionParser[Map[String, String]] ("stream-consumer") {
    opt[String] ("cassandra.contact.point").optional().text("Cassandra contact point").action {
      (value, config) =>
        config.updated ("cassandra.contact.point", value)
    }
    opt[String] ("kafka.bootstrap.servers").optional().text("Kafka bootstrap servers").action {
      (value, config) =>
        config.updated ("kafka.bootstrap.servers", value)
    }
    opt[String] ("kafka.topic").optional().text("Kafka topic").action {
      (value, config) =>
        config.updated ("kafka.topic", value)
    }
    opt[String] ("schema.registry.url").optional().text("Schema Registry url").action {
      (value, config) =>
        config.updated ("schema.registry.url", value)
    }
    opt[String] ("avro.logline.source").optional().text("Avro logline source").action {
      (value, config) =>
        config.updated ("avro.logline.source", value)
    }
    opt[String] ("zipkin.kafka.broker.list").optional().text("Zipkin Kafka broker list").action {
      (value, config) =>
        config.updated ("zipkin.kafka.broker.list", value)
    }
    opt[String] ("zipkin.sample.rate").optional().text("Zipkin sample rate").action {
      (value, config) =>
        config.updated ("zipkin.sample.rate", value)
    }
    opt[String] ("zipkin.kafka.topic").optional().text("Zipkin Kafka topic").action {
      (value, config) =>
        config.updated ("zipkin.kafka.topic", value)
    }
  }
}


class Config(props: Properties) {

  //val WkFeedHost = props.getProperty("wikipedia.feed.host")
  //val WkFeedPort = props.getProperty("wikipedia.feed.port").toInt
  //val WkFeedChannel = props.getProperty("wikipedia.feed.channel")

  val CassandraContactPoint = props.getProperty("cassandra.contact.point")

  val KafkaBootstrapServers = props.getProperty("kafka.bootstrap.servers")

  val KafkaTopic = props.getProperty("kafka.topic")

  val SchemaRegistryUrl = props.getProperty("schema.registry.url")

  val AvroLoglineSource = props.getProperty("avro.logline.source")

  val ZipkinKafkaBrokerList = props.getProperty("zipkin.kafka.broker.list")

  val ZipkinSampleRate = props.getProperty("zipkin.sample.rate").toInt

  val ZipkinKafkaTopic = props.getProperty("zipkin.kafka.topic")
}