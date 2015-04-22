package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PongActor extends Actor with ActorLogging {
  import PongActor._

  // again partial receive function to check for inbox messages
  def receive = {
  	case PingActor.PingMessage(text) =>
  	  log.info("In PongActor - received message: {}", text)
      // sender is the Actor reference to the actor which send the message that is currently processed
  	  sender ! PongMessage("pong")
  }
}

object PongActor {
  // used to reference the actor
  val props = Props[PongActor]
  // case classes to define possible messages
  case class PongMessage(text: String)
}
