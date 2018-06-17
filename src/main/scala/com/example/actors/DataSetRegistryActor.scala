package com.example.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.example.registry.DataSetRegistry._
import spray.json.JsString
import spray.json.JsValue
import java.time.Instant
import java.util.UUID

import akka.kafka.ProducerSettings
import akka.serialization.ByteArraySerializer
import com.example.serializers.CustomerInteractionJsonSerializer
import org.apache.kafka.common.serialization.{StringSerializer}

object DataSetRegistryActor {
  final case class RawDataSet(msg: JsValue)
  final case class IngestDataSet(msg: DataSetFromJson)
  final case class FinishedIngestion(msg: DataSetReceipt)

  def props: Props = Props[DataSetRegistryActor]
}


class DataSetRegistryActor extends Actor with ActorLogging {
  import DataSetRegistryActor._

//  val config = system.settings.config
//  val producerSettings = ProducerSettings(config, new CustomerInteractionJsonSerializer, new ByteArraySerializer)
//      .withBootstrapServers("localhost:9092")

  val provider = "com.example.DataCollectorApi"

  def receive: Receive = {

    case RawDataSetFromJson(json, version) =>
      log.info(s"Received following raw json: ${json}")
      val dst = inferDataSetType(msg=json, version=version)
      val metadata = getIngestionMetadata(version)

      val dsr = DataSetReceipt(data_set_type = dst, metadata = metadata, description = s"${dst.content_type} ingested")

      sender() ! FinishedIngestion(dsr)
  }

  def inferDataSetType(msg: JsValue, version: String): DataSetType = {
    val contentType = msg.asJsObject.getFields("event_name") match {
      case Seq(JsString(event_name)) => event_name
      case _ => "invalid"
    }
    DataSetType(provider = provider, content_type = contentType, content_version = version)
  }

  def getIngestionMetadata(version: String): DataSetIngestionMetadata = {
    val timestamp: Instant = Instant.now()
    val uuid = UUID.randomUUID()
    DataSetIngestionMetadata(uuid=s"${uuid}", timestamp = s"${timestamp}")
  }

  def publishToKafka() = {

  }

}