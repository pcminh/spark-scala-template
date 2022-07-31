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
import org.apache.spark.sql.functions.udf

/** This contains the user defined functions used by the DAG [[Steps]].
  *
  * Every function is provided as plain function and as functionUDF wrapped by
  * org.apache.spark.sql.functions.udf. The former is easier to unit test,
  * the later is used in a real Spark DAG step.
  *
  * Lazy evaluation and higher order functions with closure can be used for
  * pre-initialization and maintaining serializability.
  */
object Functions extends LazyLogging {

  /** Converts a `&` separated string of `=` separated key, value pairs into a Map.
    *
    * @param ks A string like `key1=value1&key2=value2&key3=value3&...`
    * @return `Map("key1" -> "value1", "key2" -> "value2", ...)` or empty Map
    *              in case of a non parseable string.
    */
  def keyValueStringToMap(ks: String): Map[String, String] =
    Option(ks)
      .getOrElse("")
      .split("&")
      .map(_.split("="))
      .filter(_.size == 2)
      .map { case Array(k, v) => (k, v) }
      .toMap
  def keyValueStringToMapUDF = udf(keyValueStringToMap _)
}
