package com.example.routes.v1

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{as, entity, onSuccess, pathPrefix}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.{get, post}
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.pattern.ask
import akka.util.Timeout
import com.example.JsonSupport
import com.example.actors.MessageRegistryActor.{FinishedIngestion, CreateMsg, GetMsgs}
import com.example.actors.{AppEvent, AppEvents}

import scala.concurrent.Future
import scala.concurrent.duration._


class ApiRoute(actor: ActorRef) extends JsonSupport {

  implicit lazy val timeout = Timeout(5.seconds)

  lazy val apiRoute: Route = pathPrefix("ingest") {
    get {
      val events: Future[AppEvents] =
        (actor ? GetMsgs).mapTo[AppEvents]
      complete(events)
    }
    post {
      entity(as[AppEvent]) { appEvent =>
        val eventIngested: Future[FinishedIngestion] =
          (actor ? CreateMsg(appEvent)).mapTo[FinishedIngestion]

        onSuccess(eventIngested) { performed =>
          println("Created user [{}]: {}", appEvent.name, performed.description)
          complete((StatusCodes.Created, performed))
          // b
          // t
        }
      }
    }
  }
}
