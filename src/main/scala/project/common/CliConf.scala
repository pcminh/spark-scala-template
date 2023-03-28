package project.common

import org.rogach.scallop._

/** Main cli parsing class
  *
  * @param arguments
  *   The unparsed command line arguments
  */
class CliConf(arguments: Seq[String]) extends ScallopConf(arguments) {
  appendDefaultToDescription = true
  val nodes = opt[String]("nodes", descr = "Spark nodes (local run)", default = Option("local[*]"))
  val stay = opt[Boolean](
    "stay",
    descr =
      "Wait for key press to exit (to keep SparkSession and webserver running while debugging)",
    default = Option(false)
  )
  val input = opt[String](
    "input",
    descr = "Path to the raw data to process (local, hdfs, s3) or SQL statement (sql)",
    required = true
  )
  val inputType = opt[String](
    "inputType",
    descr = "Generic Load/Save Functions Format Type (json, csv, orc, parquet)",
    required = true
  )
  val output = opt[String]("output", descr = "Output path (local, hdfs, s3)", required = true)
  val limit =
    opt[Int](
      "limit",
      descr = "Limit DAG steps to given number, the read and write/show steps are always added"
    )
  val linesToShow = opt[Int](
    "linesToShow",
    descr =
      "Amount of lines to shows to the console (instead of writing snappy compressed parquet files)"
  )
  val debug = opt[Boolean](
    "debug",
    descr = "Explains plan during DAG construction",
    default = Option(false)
  )

  verify()
}
