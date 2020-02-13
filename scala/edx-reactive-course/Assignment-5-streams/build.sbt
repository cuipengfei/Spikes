course := "reactive"
assignment := "streaming"

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
//  "org.scalacheck"           %% "scalacheck"               % "1.14.0"    % Test,
  "com.novocode"             % "junit-interface"           % "0.11"      % Test
)
