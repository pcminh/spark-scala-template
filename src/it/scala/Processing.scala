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
import project.Processing
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.scalatest.FunSuite

@SuppressWarnings(Array("org.wartremover.warts.Nothing"))
class ProcessingTest extends FunSuite with DataFrameSuiteBase {
  test("the whole Processing DAG") {
    val input                  = "./data/*.gz"
    val stepLimit: Option[Int] = None
    val debug                  = false

    val dag = new Processing(spark)

    val steps = dag.dagSteps

    val rawData  = dag.steps.read(input)
    val fullData = dag.combineSteps(steps, rawData, stepLimit, debug)

    assert(fullData.collectAsList().size == 1)
  }
}
