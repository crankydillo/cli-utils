lazy val commonSettings = Seq(
  organization := "org.beeherd",
  version := "0.1.0"
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "cli-utils",
    libraryDependencies ++= Seq(
      "org.scala-tools.testing" %% "specs" % "1.6.9" % "test"
    )
  )
