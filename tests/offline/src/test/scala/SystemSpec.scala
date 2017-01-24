package info.hupel.isabelle.tests

import java.nio.file.Paths

import scala.concurrent._
import scala.concurrent.duration._
import scala.math.BigInt

import org.specs2.Specification
import org.specs2.specification.core.Env

import info.hupel.isabelle._
import info.hupel.isabelle.api._
import info.hupel.isabelle.pure._
import info.hupel.isabelle.hol._

class SystemSpec(val specs2Env: Env) extends Specification with DefaultSetup with IsabelleMatchers { def is = s2"""

  Isabelle system

  An Isabelle system
    detects startup failures   $failingSystem
    detects missing protocol   $missingProtocol"""

  def create(session: String) =
    isabelleEnv.flatMap(System.create(_, resources.makeConfiguration(Nil, session)))

  def check(session: String, reason: System.StartupException.Reason) =
    create(session).failed must be_===(System.StartupException(reason): Throwable).awaitFor(duration)

  val failingSystem = check("Unbuilt_Session", System.StartupException.Exited)
  val missingProtocol = check("Pure", System.StartupException.NoPong)

}
