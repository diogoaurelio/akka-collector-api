package com.example.actors

import akka.actor.{Actor, ActorLogging, Props}

// List[Map[Any, Any]]
final case class AppEvent(name: String, age: Int, payload: String)
final case class AppEvents(es: Seq[AppEvent])

object MessageRegistryActor {
  final case class FinishedIngestion(description: String)
  final case object GetMsgs
  final case class CreateMsg(msg: AppEvent)
  final case class GetMsg(name: String)

  def props: Props = Props[MessageRegistryActor]
}


class MessageRegistryActor extends Actor with ActorLogging {
  import MessageRegistryActor._

  var events = Set.empty[AppEvent]

  def receive: Receive = {
    case GetMsgs =>
      sender() ! AppEvents(events.toSeq)
    case CreateMsg(event) =>
      events += event
      sender() ! FinishedIngestion(s"User ${event.name} created with age ${event.age} and payload ${event.payload}.")
    case GetMsg(name) =>
      sender() ! events.find(_.name == name)
  }
}