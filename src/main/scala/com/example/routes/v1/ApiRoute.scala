package com.example.routes.v1

//import akka.actor.Status.{Failure, Success}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, entity, onComplete, pathPrefix}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{get, post}
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout
import com.example.registry.JsonEnvelope
import com.example.actors.DataSetRegistryActor.{FinishedIngestion, IngestDataSet}
import com.example.registry.DataSetRegistry._
import spray.json.JsValue

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure => ScalaFailure, Success => ScalaSuccess}

class ApiRoute(actor: ActorRef) extends JsonEnvelope {

  lazy val apiVersion = "v1.0"
  implicit lazy val timeout = Timeout(5.seconds)

  lazy val apiRoute: Route = pathPrefix("ingest") {
    post {
      entity(as[JsValue]) { event =>
        val eventIngested: Future[FinishedIngestion] =
          (actor ? RawDataSetFromJson(event, apiVersion)).mapTo[FinishedIngestion]

        onComplete(eventIngested) {
          case ScalaSuccess(success) => {
            val dsr = success.msg
            complete((StatusCodes.Accepted, dsr))
          }
          case ScalaFailure(ex) => complete((StatusCodes.InternalServerError,  s"An error occurred: ${ex.getMessage}"))
        }
      }
    }
  }
}
