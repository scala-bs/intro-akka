package com.example

import akka.actor.{Actor, ActorLogging, Props, ActorRef}

class PingActor extends Actor with ActorLogging {
  // import the object from below
  import PingActor._

  // create a counter var
  var counter : Int = 0
  // use the context (which references to the actor system of this actor) to create a pong actor
  val pongActor : ActorRef = context.actorOf(PongActor.props, "pongActor")

  // receive method that is used to iterate through inbox messages of an actor
  def receive = {
  	case Initialize =>
	    log.info("In PingActor - starting ping-pong")
      // sends a message to the pong actor
  	  pongActor ! PingMessage("ping")
      // this would wait for an answer from the pong actor (which would be a future)
      //val test : Future[Any] = pongActor ? PingMessage("Hello")
  	case PongActor.PongMessage(text) =>
  	  log.info("In PingActor - received message: {}", text)
  	  counter += 1
      // shut down the system if 3 is reached or send another message
  	  if (counter == 3) context.system.shutdown()
  	  else sender ! PingMessage("ping")
  }
}

object PingActor {
  // used to reference to the actor
  val props = Props[PingActor]
  // case classes that are used as messages
  case object Initialize
  case class PingMessage(text: String)
}
