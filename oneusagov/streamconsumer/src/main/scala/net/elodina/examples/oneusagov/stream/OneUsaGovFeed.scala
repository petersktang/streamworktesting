package net.elodina.examples.oneusagov.stream


import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.stream.ActorMaterializer
import org.codehaus.jackson.map.ObjectMapper
import org.slf4j.LoggerFactory
import java.util.concurrent.BlockingQueue
import akka.event.Logging.DefaultLogger
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent._


/**
  * Created by osboxes on 25/05/16.
  */
class OneUsaGovFeed(messageQueue: BlockingQueue[FeedEvent], host: String = "http://developer.usa.gov/1usagov") extends Runnable {


  // set this to true to stop the thread

  val log = LoggerFactory.getLogger(this.getClass)
  val jsonMapper = new ObjectMapper()

  var shutdownFlag = false

  def shutdown() {
    shutdownFlag = true
  }


  override def run() {
    

    implicit val system = ActorSystem("test")
    import system.dispatcher

    implicit val materializer = ActorMaterializer()
    val source = Uri(host)

    val finished = Http().singleRequest(HttpRequest(uri = source)).flatMap { response =>

      response.entity.dataBytes.runForeach { chunk =>

        //pass each click into in-memory queue

        val feedEvent = new FeedEvent(System.currentTimeMillis(), host, "anonymous", chunk.utf8String)

        messageQueue.put(feedEvent)
        log.debug("chunk saved to queue: " + println(chunk.utf8String))

      }
    }


    while(!shutdownFlag) {

      Thread.sleep(1000)

    }


  }

}





