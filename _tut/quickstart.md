## Quickstart




```scala
import scala.concurrent._, scala.concurrent.duration._
// import scala.concurrent._
// import scala.concurrent.duration._

import monix.execution.Scheduler.Implicits.global
// import monix.execution.Scheduler.Implicits.global

import info.hupel.isabelle._, info.hupel.isabelle.api._, info.hupel.isabelle.setup._
// import info.hupel.isabelle._
// import info.hupel.isabelle.api._
// import info.hupel.isabelle.setup._

val setup = Setup.default(Version.Stable("2016-1"), false).right.get
// setup: info.hupel.isabelle.setup.Setup = Setup(/home/travis/.local/share/libisabelle/setups/Isabelle2016-1,Linux,<Isabelle2016-1>)

val resources = Resources.dumpIsabelleResources().right.get
// resources: info.hupel.isabelle.setup.Resources = Resources(/tmp/libisabelle_resources32333048907018178,List(/tmp/libisabelle_resources32333048907018178/libisabelle, /tmp/libisabelle_resources32333048907018178/classy, /tmp/libisabelle_resources32333048907018178/multi-isabelle))

val config = Configuration.simple("Protocol")
// config: info.hupel.isabelle.api.Configuration = session Protocol

val transaction =
  for {
    env <- setup.makeEnvironment(resources, Nil)
    sys <- System.create(env, config)
    response <- sys.invoke(Operation.Hello)("world")
    () <- sys.dispose
  } yield response.unsafeGet
// transaction: scala.concurrent.Future[String] = Future(<not completed>)

val response = Await.result(transaction, Duration.Inf)
// response: String = Hello world

assert(response == "Hello world")
```
