package com.example

import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.{ TestActors, TestKit, ImplicitSender }
import org.scalatest.WordSpecLike
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll

class PingPongActorSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  // create the actor system for testing purpose
  def this() = this(ActorSystem("MySpec"))

  // define the function that will shut everything down at the end
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  // define a test for the ping actor
  "A Ping actor" must {
    "send back a ping on a pong" in {
      // create the ping actor
      val pingActor = system.actorOf(PingActor.props)
      // send a pong message to the ping actor
      pingActor ! PongActor.PongMessage("pong")
      // check if the next message that is send is a ping message
      expectMsg(PingActor.PingMessage("ping"))
    }
  }

  // define a test for the pong actor
  "A Pong actor" must {
    "send back a pong on a ping" in {
      // create the pong actor
      val pongActor = system.actorOf(PongActor.props)
      // send a ping message to the pong actor
      pongActor ! PingActor.PingMessage("ping")
      // check if the next message that is send is a pong message
      expectMsg(PongActor.PongMessage("pong"))
    }
  }

}
