package net.elodina.examples.oneusagov.stream

import scala.beans.BeanProperty

/**
  * Created by osboxes on 25/05/16.
  */


//Locally defined case class (structure only) of an avro class defined in the common library
case class FeedEvent(@BeanProperty time: Long,
                              @BeanProperty channel: String,
                              @BeanProperty source: String,
                              @BeanProperty rawEvent: String)