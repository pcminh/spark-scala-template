/*
 * Copyright 2011-2016 Marconi Lanna
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// http://www.scalastyle.org/
// http://github.com/scalastyle/scalastyle-sbt-plugin
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.9.0")

// http://github.com/puffnfresh/wartremover
addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.1.1")

// http://github.com/sksamuel/scapegoat
// http://github.com/sksamuel/sbt-scapegoat
addSbtPlugin("com.sksamuel.scapegoat" %% "sbt-scapegoat" % "1.0.4")

// http://github.com/scoverage/sbt-scoverage
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

// http://github.com/scala-ide/scalariform
// http://github.com/sbt/sbt-scalariform
//addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// Adds a `dependencyUpdates` task to check Maven repositories for dependency updates
// http://github.com/rtimush/sbt-updates
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.1")

// Adds a `assembly` task to create a fat JAR with all of its dependencies
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")

// http://github.com/sbt/sbt-license-report
addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.2.0")

// http://github.com/orrsella/sbt-stats
addSbtPlugin("com.orrsella" % "sbt-stats" % "1.0.5")

// http://github.com/alexarchambault/coursier
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC10")

// Creates Eclipse project definitions (.classpath, .project, .settings/)
// http://github.com/typesafehub/sbteclipse
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.1")

// Adds a `ensimeConfig` task to generate ensime project definitions
// http://ensime.org/build_tools/sbt/
addSbtPlugin("org.ensime" % "sbt-ensime" % "1.12.14")

// A Sbt plugin that fills apiMappings for common Scala libraries.
// https://github.com/ThoughtWorksInc/sbt-api-mappings/releases
addSbtPlugin("com.thoughtworks.sbt-api-mappings" % "sbt-api-mappings" % "1.1.0")

// A git plugin for SBT https://github.com/sbt/sbt-git
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

// disables warning: SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
// as explained here https://github.com/sbt/sbt-git
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25"
