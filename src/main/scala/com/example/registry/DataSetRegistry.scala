package com.example.registry

import spray.json.JsValue

object DataSetRegistry {
  final case class DataSetFromJson(uuid: String, event_name: String, provider: String, version: String, published_at: String, data: JsValue)
  final case class DataSetsFromJson(es: Seq[DataSetFromJson])

  final case class DataSetType(provider: String, content_type: String, content_version: String)
  final case class DataSetReceipt(data_set_type: DataSetType, provider_uuid: String, ingested_at: String, description: String)
}
