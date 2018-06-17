package com.example.registry

import spray.json.JsValue

object DataSetRegistry {
  final case class RawDataSetFromJson(json: JsValue, version: String)

  final case class DataSetFromJson(uuid: String, event_name: String, provider: String, version: String, published_at: String, data: JsValue)
  final case class DataSetsFromJson(es: Seq[DataSetFromJson])

  final case class DataSetType(provider: String, content_type: String, content_version: String)
  final case class DataSetIngestionMetadata(uuid: String, timestamp: String)

  final case class DataSetReceipt(data_set_type: DataSetType, metadata: DataSetIngestionMetadata, description: String)

  final case class CustomerInteraction(user_id: String, tracking_version: String, user_action: String, timestamp: String)
}
