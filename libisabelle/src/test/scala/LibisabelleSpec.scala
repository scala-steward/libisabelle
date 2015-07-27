package edu.tum.cs.isabelle

import scala.concurrent.duration._

import edu.tum.cs.isabelle.defaults._

import isabelle.Exn

import org.specs2.Specification
import org.specs2.concurrent.ExecutionEnv
import org.specs2.matcher.Matcher

class LibisabelleSpec extends Specification with IsabelleMatchers { def is = s2"""

  Basic protocol interaction

  An Isabelle session
    can be started          ${implicit env: ExecutionEnv =>
      system must exist.awaitFor(30.seconds)
    }
    can load theories       ${implicit env: ExecutionEnv =>
      loaded must beRes(()).awaitFor(30.seconds)
    }
    reacts to requests      ${implicit env: ExecutionEnv =>
      response must beRes("prop => prop => prop").awaitFor(30.seconds)
    }
    can be torn down        ${implicit env: ExecutionEnv =>
      teardown must exist.awaitFor(30.seconds)
    }"""


  val TypeOf = Operation.implicitly[String, String]("type_of")

  val system = System.instance(Some(new java.io.File(".")), "Protocol")
  val loaded = system.flatMap(_.invoke(Operation.UseThys)(List("libisabelle/src/test/isabelle/Test")))
  val response = for { s <- system; _ <- loaded; res <- s.invoke(TypeOf)("op ==>") } yield res
  val teardown = for { s <- system; _ <- response /* wait for response */; _ <- s.dispose } yield ()

  def exist[A]: Matcher[A] = ((a: A) => a != null, "doesn't exist")

}
