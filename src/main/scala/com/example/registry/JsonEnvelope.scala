package com.example.registry

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.actors.DataSetRegistryActor.FinishedIngestion
import spray.json.DefaultJsonProtocol

trait JsonEnvelope extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._
  import DataSetRegistry._

  // incoming
  implicit val v1IncomingJsonEventEnvelope = jsonFormat6(DataSetFromJson)
  implicit val eventsJsonFormat = jsonFormat1(DataSetsFromJson)

  // receipt
  implicit val dataSetType = jsonFormat3(DataSetType)
  implicit val dataSetReceipt = jsonFormat4(DataSetReceipt)

}
