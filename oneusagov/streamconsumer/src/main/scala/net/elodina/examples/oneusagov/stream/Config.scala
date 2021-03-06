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

    opt[String] ("kafka.bootstrap.servers").optional().text("Kafka bootstrap servers").action {
      (value, config) =>
        config.updated ("kafka.bootstrap.servers", value)
    }

    opt[String] ("kafka.topic").optional().text("Kafka topic").action {
      (value, config) =>
        config.updated ("kafka.topic", value)
    }

    opt[String] ("oneusagov.stream.address").optional().text("Click stream address").action {
      (value, config) =>
        config.updated ("oneusagov.stream.address", value)
    }


  }
}


class Config(props: Properties) {

  val KafkaBootstrapServers = props.getProperty("kafka.bootstrap.servers")

  val KafkaTopic = props.getProperty("kafka.topic")

  val OneUsaGovStreamAddress = props.getProperty("oneusagov.stream.address")


}