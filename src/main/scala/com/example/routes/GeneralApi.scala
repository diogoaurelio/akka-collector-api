package com.example.routes

import akka.actor.ActorRef
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import akka.http.scaladsl.server.{PathMatcher, Route}
import akka.util.Timeout
import scala.concurrent.duration._
import com.example.routes.v1.{ApiRoute => V1Api}
import akka.http.scaladsl.model.StatusCodes



class GeneralApi(val ingestionActor: ActorRef) {

  implicit lazy val timeout = Timeout(5.seconds)

  lazy val version = PathMatcher("""v([0-9]\.[0-9])""".r)
  lazy val apiRoute: Route = pathPrefix("api") {
    pathPrefix(version) {
      case "1.0" => new V1Api(ingestionActor).apiRoute
      case _ => complete(StatusCodes.Unauthorized, "Nothing to see here")
    }
  }

}
