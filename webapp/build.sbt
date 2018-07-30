name := "twitter-sentiment-analysis-webapp"

version := "1.0"

playScalaSettings

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "org.twitter4j" % "twitter4j-core" % "4.0.2"
)
