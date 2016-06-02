package net.elodina.examples.oneusagov.stream


import net.elodina.common.stream.KafkaProducerHelper;
import java.util.concurrent.{BlockingQueue, TimeUnit}
import org.slf4j.LoggerFactory


/**
  * Created by osboxes on 25/05/16.
  */
class KafkaLoader( messageQueue: BlockingQueue[FeedEvent], config: Config) extends Runnable {

  private val log = LoggerFactory.getLogger(this.getClass)

  var eventCounter = 0
  var produceCounter = 0  
  val producer = new KafkaProducerHelper(config.KafkaBootstrapServers)

  var shutdownFlag = false

  def shutdown() {
    shutdownFlag = true
  }


  override def run() {

    while(!shutdownFlag) {


      //poll queue for short period
      val item = messageQueue.poll(100, TimeUnit.MILLISECONDS)

      //return from this run method. I.E. quit writing to kafka
      if (shutdownFlag) {

        return
      }

      //if the queue had an item, send it to kafka
      if (item != null) {

        log.debug("Got an event {}", item)


        try {

          
          eventCounter += 1
          if (eventCounter % 100 == 0) log.info("Consumed {} wikipedia edits events by now", eventCounter)

          //pass to kafka          
          producer.produce("key", "record", config.KafkaTopic)
          
          produceCounter += 1
          log.debug("Successfully produced {} to Kafka, \n record=" + item.toString())
          if (produceCounter % 100 == 0) log.info("Produced {} messages to Kafka be now", item)
        } catch {          
          case e: Exception =>
            log.warn(s"Failed to process event $item, ignoring it and continue", e)
        }

      }

    }
  }

}
