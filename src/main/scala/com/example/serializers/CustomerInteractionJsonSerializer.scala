package com.example.serializers

import spray.json.JsValue
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.common.errors.SerializationException
import java.io.UnsupportedEncodingException

class CustomerInteractionJsonSerializer extends Serializer[JsValue] {
  private val encoding = "UTF8"

  override def configure(configs: java.util.Map[String, _], isKey: Boolean): Unit = {
    // nothing to do
  }

  override def serialize(topic: String, data: JsValue): Array[Byte] = {
    val opData: Option[JsValue] = Option(data)
    try {
      opData.map(_.toString.getBytes(encoding)).orNull
    } catch {
      case e: UnsupportedEncodingException =>
        throw new SerializationException("Error when serializing JsValue (toString) to Array[Byte] due to unsupported encoding " + encoding)
    }
  }

  override def close(): Unit = {
    // nothing to do
  }

}
