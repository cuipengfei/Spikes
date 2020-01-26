course := "reactive"
assignment := "protocols"

testOptions in Test += Tests.Argument(TestFrameworks.JUnit, "-a", "-v", "-s")
parallelExecution in Test := false

val akkaVersion = "2.6.0"

scalaVersion := "2.13.1"
scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-Xlint",
)

libraryDependencies ++= Seq(
  "com.typesafe.akka"        %% "akka-actor-typed"         % akkaVersion,
  "com.typesafe.akka"        %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalacheck"           %% "scalacheck"               % "1.14.0"    % Test,
  "com.novocode"             % "junit-interface"           % "0.11"      % Test
)
