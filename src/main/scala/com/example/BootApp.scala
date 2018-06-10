package com.example

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.example.actors.{MessageRegistryActor, UserRegistryActor}
import com.example.routes.GeneralApi

import scala.concurrent.Await
import scala.concurrent.duration.Duration

//#main-class
object BootApp extends App with UserRoutes {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //#server-bootstrapping

  val msgRegistryActor: ActorRef = system.actorOf(MessageRegistryActor.props, "messageRegistryActor")
  lazy val routes = new GeneralApi(ingestionActor=msgRegistryActor).apiRoute

  //#http-server
  Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)

}

