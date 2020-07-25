
name := """playAssignment"""
organization := "vub"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  "com.adrianhurt" %% "play-bootstrap" % "1.5.1-P27-B4",
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
)

logLevel := Level.Debug