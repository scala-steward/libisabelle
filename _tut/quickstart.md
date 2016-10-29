## Quickstart

```scala
import scala.concurrent._, scala.concurrent.duration._, scala.concurrent.ExecutionContext.Implicits.global
// import scala.concurrent._
// import scala.concurrent.duration._
// import scala.concurrent.ExecutionContext.Implicits.global

import info.hupel.isabelle._, info.hupel.isabelle.api._, info.hupel.isabelle.setup._
// import info.hupel.isabelle._
// import info.hupel.isabelle.api._
// import info.hupel.isabelle.setup._

val setup = Setup.default(Version("2016")).right.get
// setup: info.hupel.isabelle.setup.Setup = Setup(/home/travis/.local/share/libisabelle/setups/Isabelle2016,Linux,<Isabelle2016>,info.hupel.isabelle.impl)

val transaction =
  for {
    env <- setup.makeEnvironment
    resources = Resources.dumpIsabelleResources().right.get
    config = resources.makeConfiguration(Nil, "Protocol")
    sys <- System.create(env, config)
    response <- sys.invoke(Operation.Hello)("world")
    () <- sys.dispose
  } yield response.unsafeGet
// transaction: scala.concurrent.Future[String] = List()

val response = Await.result(transaction, Duration.Inf)
// response: String = Hello world

assert(response == "Hello world")
```
