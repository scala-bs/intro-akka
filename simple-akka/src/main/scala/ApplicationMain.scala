package com.example

import akka.actor.{ActorSystem, ActorRef}

object ApplicationMain extends App {
  // create the actor system
  val system : ActorSystem = ActorSystem("MyActorSystem")
  // register the ping actor as actor in this system (reference to actor is returned)
  val pingActor : ActorRef = system.actorOf(PingActor.props, "pingActor")
  // send init command to the ping actor
  pingActor ! PingActor.Initialize

  // wait until the actorsystem is terminated
  system.awaitTermination()
}
