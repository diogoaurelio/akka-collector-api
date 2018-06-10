package com.example.actors

import akka.actor.{Actor, ActorLogging, Props}

import com.example.registry.DataSetRegistry._

object DataSetRegistryActor {
  final case class IngestDataSet(msg: DataSetFromJson)
  final case class FinishedIngestion(msg: DataSetReceipt)

  def props: Props = Props[DataSetRegistryActor]
}


class DataSetRegistryActor extends Actor with ActorLogging {
  import DataSetRegistryActor._

  val provider = "ExampleCollectorApi"
  

  def receive: Receive = {
    case IngestDataSet(msg) =>
      log.info(s"Received request to ingested DataSet: ${msg}")
      val dst = DataSetType(provider = provider, content_type = msg.event_name, content_version = msg.version)
      val dsr = DataSetReceipt(data_set_type = dst, provider_uuid = "blah", ingested_at = "buh", description = "Successfully ingested msg")
      sender() ! FinishedIngestion(msg=dsr)
  }
}