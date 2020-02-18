course := "reactive"
assignment := "followers"

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
  "com.typesafe.akka"        %% "akka-stream"              % akkaVersion,
  "com.typesafe.akka"        %% "akka-stream-testkit"      % akkaVersion % Test,
  "com.typesafe.akka"        %% "akka-stream-typed"        % akkaVersion,
  "com.typesafe.akka"        %% "akka-actor-typed"         % akkaVersion,
  "com.novocode"             % "junit-interface"           % "0.11"      % Test
)
