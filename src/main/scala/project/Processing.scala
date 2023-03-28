/*
 * Copyright 2017 Daniel Bast
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
package project

import common.{ CliConf, ProcessingBase }
import buildinfo.BuildInfo
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

class Processing(spark: SparkSession) extends ProcessingBase(spark) {
  override val steps = new Steps(spark)

  override val dagSteps = Vector(
    steps.decodeData _,
    steps.selectFinalFields _
  )
}

/** Main entrypoint, parses the command line arguments, gets or creates the SparkSession und starts
  * the DAG processing.
  *
  * @param args
  *   The unparsed command line arguments
  */
object Processing extends LazyLogging {
  def main(args: Array[String]): Unit = {
    logger.info(s"Starting '${BuildInfo.name}' version '${BuildInfo.version}'")

    val conf = new CliConf(args)
    logger.info(s"The command line parameters are: ${conf.summary}")

    lazy val spark = SparkSession.builder
      .master(conf.nodes())
      .appName(BuildInfo.name)
      .getOrCreate()

    val processing = new Processing(spark)
    try
      processing.process(
        input = conf.input(),
        inputType = conf.inputType(),
        output = conf.output(),
        limit = conf.limit.toOption,
        lines = conf.linesToShow.toOption,
        debug = conf.debug()
      )
    finally {
      if (conf.stay()) {
        logger.info("Waiting: press enter to exit")
        logger.info(System.in.read().toString)
      }
      spark.stop()
    }
  }
}
