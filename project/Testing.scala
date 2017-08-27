import sbt._
import sbt.Keys._

object Testing {
  val IntegrationTest = config("it") extend(Test)
  val EndToEndTest = config("e2e") extend(Runtime)
  val all = Seq(IntegrationTest, EndToEndTest)

  val testAll = TaskKey[Unit]("test-all")

  val testSettings = Seq(
    fork in Test := true,
    parallelExecution in Test := true
  )

  val itSettings = inConfig(IntegrationTest)(Defaults.testSettings) ++ Seq(
    fork in IntegrationTest := true,
    parallelExecution in IntegrationTest := false,
    scalaSource in IntegrationTest := baseDirectory.value / "src/it/scala"
  )

  val e2eSettings = inConfig(EndToEndTest)(Defaults.testSettings) ++ Seq(
    fork in EndToEndTest := false,
    parallelExecution in EndToEndTest := false,
    scalaSource in EndToEndTest := baseDirectory.value / "src/e2e/scala"
  )

  val settings = testSettings ++ itSettings ++ e2eSettings ++ Seq(
    testAll := (),
    testAll := testAll.dependsOn(test in EndToEndTest).value,
    testAll := testAll.dependsOn(test in IntegrationTest).value,
    testAll := testAll.dependsOn(test in Test).value
  )
}
