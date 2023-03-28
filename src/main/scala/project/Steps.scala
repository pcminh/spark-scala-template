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

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.{ Dataset, Row, SparkSession }
import org.apache.spark.sql.functions._
import Functions._

/** This contains the Steps to build a complete DAG
  *
  * Steps shall be written by using the [[org.apache.spark.sql.Dataset]] API, functions from
  * [[org.apache.spark.sql.functions]] and user defined functions from [[Functions]]. There shall be
  * no complicated scala stuff in this class, to make this readable and reviewable for everyone
  * understanding SQL.
  */
class Steps(spark: SparkSession) extends LazyLogging {
  import spark.implicits._

  /** Read data from local / hdfs / s3 source, the first step of a DAG
    *
    * @param input
    *   Path to the data to read
    * @param inputType
    *   Generic Load/Save Functions Format Type (json, csv, orc, parquet)
    */
  def read(input: String, inputType: String): Dataset[Row] =
    if (inputType == "sql") spark.sql(input) else spark.read.format(inputType).load(input)

  def decodeData(df: Dataset[Row]): Dataset[Row] =
    df.withColumn("data", keyValueStringToMapUDF(unbase64($"raw_data")))

  def selectFinalFields(df: Dataset[Row]): Dataset[Row] =
    df.select("data.key1")

  /** Write or show the data of a Dataset. This is the last step of a DAG.
    *
    * This step can do two things: write compressed parquet files or show a specified number of
    * lines. The last one is for developing / debugging.
    *
    * @param output
    *   The local / hdfs / s3 path to store the data
    * @param linesToShow
    *   Overwrites `writeCsv` if any value is given
    */
  def writeOrShowData(df: Dataset[Row], output: String, linesToShow: Option[Int]): Unit =
    if (linesToShow.isDefined) {
      df.show(linesToShow.getOrElse(0), false)
    } else {
      df.write
        .mode("overwrite")
        .parquet(output)
    }
}
