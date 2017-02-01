package info.hupel.isabelle.japi

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import monix.execution.Scheduler.Implicits.global

import info.hupel.isabelle.api._
import info.hupel.isabelle.setup._

object JSetup {

  def defaultSetup(version: Version, timeout: Duration): Setup =
    Setup.default(version) match {
      case Left(reason) => sys.error(reason.explain)
      case Right(setup) => setup
    }

  def defaultSetup(version: Version): Setup =
    defaultSetup(version, Duration.Inf)

  def makeEnvironment(setup: Setup, config: Configuration, timeout: Duration): Environment =
    Await.result(setup.makeEnvironment(config), timeout)

  def makeEnvironment(setup: Setup, config: Configuration): Environment =
    makeEnvironment(setup, config, Duration.Inf)

}
