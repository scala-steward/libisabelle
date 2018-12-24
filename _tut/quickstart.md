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

val setup = Setup.default(Version.Stable("2017"), false).right.get
// setup: info.hupel.isabelle.setup.Setup = Setup(/home/travis/.local/share/libisabelle/setups/Isabelle2017,Linux,<Isabelle2017>)

val resources = Resources.dumpIsabelleResources().right.get
// resources: info.hupel.isabelle.setup.Resources = Resources(/tmp/libisabelle_resources2333706299954605357,List(/tmp/libisabelle_resources2333706299954605357/libisabelle, /tmp/libisabelle_resources2333706299954605357/classy))

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
