package net.elodina.examples.oneusagov.stream

import java.io.File

import java.util.concurrent.LinkedBlockingQueue

//import com.github.kristofa.brave.KafkaZipkinTracing._

//import consumer.{Config, usagov}
import org.slf4j.LoggerFactory

object StreamConsumerMain {
  //private val log = LoggerFactory.getLogger(this.getClass)
  private val log = LoggerFactory.getLogger(this.getClass)

  //private val objectMapper = new ObjectMapper()
  //private val writer = objectMapper.writer()

  def main(args: Array[String]) {

    println("Stream Consumer Main is running...")

//    if (args.length < 1) {
//      System.err.println("This class should be provided with at least one argument")
//      System.err.println("Usage example: bin/stream-consumer.sh config/usa-gov.stream-consumer.properties")
//      System.exit(-1)
//    }


    //http://developer.usa.gov/1usagov


    //Config
    // log.info("Loading configuration file {}", args(0))
    // val config = Config(new File(args(0)), args.tail)


    //Zipkin Init Tracing
    // initTracing(config.ZipkinKafkaBrokerList,
    //   "eeyore_stream_consumer",
    //   sampleRate = Some(config.ZipkinSampleRate),
    //   topic = Option(config.ZipkinKafkaTopic))


    //the pipeline between threads
    // val queue = new LinkedBlockingQueue[FeedEvent]()

    // //Thread for reading gov data
    // val feed = new OneUsaGovDataFeed(queue)
    // new Thread(feed, "one-usa-gov-feed-darren").start()

    // //Thread for saving to kafka
    // val writer = new KafkaWriter(queue, config)
    // new Thread(writer, "kafka-writer-darren").start()

    // scala.io.StdIn.readInt()


//    feed.shutdown()
//    writer.shutdown()


  }

}