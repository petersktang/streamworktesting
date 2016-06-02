package net.elodina.tools.kafka.listener

import java.io.File
import java.util.concurrent.{LinkedBlockingQueue, TimeUnit}

import net.elodina.stream.common.KafkaConsumerHelper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._


object KafkaListenerMain {

  private val log = LoggerFactory.getLogger(this.getClass)


  def main(args: Array[String]) {

    println("Kafka Listener Main is running...")

    if (args.length < 1) {
      System.err.println("This class should be provided with one argument which is a config file")
      System.exit(-1)
    }


    //Config
    log.info("Loading configuration file {}", args(0))
    val config = Config(new File(args(0)), args.tail)


    //create and configure the kafka consumer helper
    val javaList = List(config.KafkaTopic).asJava

    //create a blocking queue to read from the queue
    val queue = new LinkedBlockingQueue[ConsumerRecord[String, String]](config.ListenerBufferMaxSize.toInt)


    val consumerHelper = new KafkaConsumerHelper(config.KafkaBootstrapServers,
                              config.GroupId,
                              config.EnableAutoCommit.toBoolean,
                              config.AutoOffsetReset,
                              javaList,
                              queue)


    //start the runnable object
    new Thread(consumerHelper, "kafka-consumer-helper").start()


    WatchQueue(log, queue, config.ListenerReadWaitMs.toLong)

  }


  def WatchQueue(log: Logger, queue: LinkedBlockingQueue[ConsumerRecord[String, String]], readWaitMs: Long) : Unit = {

    var quitWatching = false

    log.trace("Press q and press enter to quit watching. Ok? Press enter to continue")

    log.trace("topic,partition,offset,key,value")

    //poll queue for short period
    while (!quitWatching) {

      //poll queue
      val item = queue.poll(100, TimeUnit.MILLISECONDS)

      if (item != null) {

        log.trace(s"${item.topic},${item.partition},${item.offset}%d,${item.key}%s,${item.value}%s")
      }

      Thread.sleep(readWaitMs)

//      if (System.in.available() > 0) {
//        val input = scala.io.StdIn.readLine()
//        if (input.equals("q")) {
//          quitWatching = true
//        }
//      }

    }


  }

}
