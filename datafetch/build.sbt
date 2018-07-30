name := "datafetch"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.32",
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "org.twitter4j" % "twitter4j-stream" % "4.0.2",
  "com.rabbitmq" % "amqp-client" % "3.3.5",
  "org.avaje" % "ebean" % "2.8.1",
  "javax.persistence" % "persistence-api" % "1.0.2",
  "org.apache.commons" % "commons-lang3" % "3.3.2",
  "org.springframework" % "spring-core" % "4.0.6.RELEASE",
  "org.springframework" % "spring-context" % "4.0.6.RELEASE",
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.13",
  "log4j" % "log4j" % "1.2.17",
  "org.apache.logging.log4j" % "log4j-core" % "2.0.2",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.4.1",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.4.1" classifier "models"
)

play.Project.playJavaSettings
