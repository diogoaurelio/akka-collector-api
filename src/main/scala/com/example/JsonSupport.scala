package com.example

import com.example.actors.{AppEvent, AppEvents, User, Users}
import com.example.actors.UserRegistryActor.ActionPerformed
import com.example.actors.MessageRegistryActor.FinishedIngestion

//#json-support
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val userJsonFormat = jsonFormat3(User)
  implicit val usersJsonFormat = jsonFormat1(Users)

  implicit val actionPerformedJsonFormat = jsonFormat1(ActionPerformed)

  // https://stackoverflow.com/questions/34977288/spray-how-to-unmarshal-a-response-of-jsarray-or-jsobject-ie-jsvalue-in-pipel
  implicit val eventJsonFormat = jsonFormat3(AppEvent)
  implicit val eventsJsonFormat = jsonFormat1(AppEvents)
  implicit val finishedIngestion = jsonFormat1(FinishedIngestion)
}
//#json-support
