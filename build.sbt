organization := "com.github"

name := """elascala"""

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.elasticsearch" % "elasticsearch" % "1.4.2",
  "com.google.code.gson" % "gson" % "2.3",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.4.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "junit" % "junit" % "4.11" % "test",
  "org.mockito" % "mockito-all" % "1.10.8" % "test"
)
