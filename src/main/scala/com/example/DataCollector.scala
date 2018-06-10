package com.example

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.actors.{DataSetRegistryActor}
import com.example.routes.GeneralApi

import scala.concurrent.Await
import scala.concurrent.duration.Duration

//#main-class
object DataCollector extends App {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("helloAkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  //#server-bootstrapping

  val msgRegistryActor: ActorRef = system.actorOf(DataSetRegistryActor.props, "dataSetRegistryActor")
  lazy val routes = new GeneralApi(ingestionActor=msgRegistryActor).apiRoute

  lazy val serverPort = 7777
  Http().bindAndHandle(routes, "localhost", serverPort)

  println(s"Server online at http://localhost:${serverPort}/")

  Await.result(system.whenTerminated, Duration.Inf)

}

