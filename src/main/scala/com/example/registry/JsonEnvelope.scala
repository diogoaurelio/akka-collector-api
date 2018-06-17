package com.example.registry

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonEnvelope extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._
  import DataSetRegistry._

  // incoming
  implicit val rawDataSetFromJson = jsonFormat2(RawDataSetFromJson)


  // receipt
  implicit val dataSetType = jsonFormat3(DataSetType)
  implicit val dataSetIngestionMetadata = jsonFormat2(DataSetIngestionMetadata)
  implicit val dataSetReceipt = jsonFormat3(DataSetReceipt)

}
