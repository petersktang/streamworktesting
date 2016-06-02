package net.elodina.examples.oneusagov.stream


import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import org.slf4j.LoggerFactory


object StreamConsumerMain {

  private val log = LoggerFactory.getLogger(this.getClass)


  def main(args: Array[String]) {

    println("Stream Consumer Main is running...")

    if (args.length < 1) {
      System.err.println("This class should be provided with one argument which is a config file")
      System.exit(-1)
    }

    //http://developer.usa.gov/1usagov


    //Config
    log.info("Loading configuration file {}", args(0))
    val config = Config(new File(args(0)), args.tail)


    //the pipeline between threads
    val queue = new LinkedBlockingQueue[FeedEvent]()

    //Thread for reading gov data
    val feed = new OneUsaGovFeed(queue)
    new Thread(feed, "one-usa-gov-feed-darren").start()


    //Thread for saving to kafka
    val writer = new KafkaLoader(queue, config)
    new Thread(writer, "kafka-writer-darren").start()


    scala.io.StdIn.readInt()

    feed.shutdown()
    writer.shutdown()

  }

}