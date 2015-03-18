/** Copyright 2015 TappingStone, Inc.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

package io.prediction.controller

import io.prediction.core.BaseEvaluator

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

import scala.reflect._
import scala.reflect.runtime.universe._
import grizzled.slf4j.Logger

/** Base class of evaluator.
  *
  * Evaluator compare predicted result with actual known values and produce numerical
  * comparisons.
  *
  * @tparam EI Evaluation Info class.
  * @tparam Q Input query class.
  * @tparam P Output prediction class.
  * @tparam A Actual value class.
  * @tparam EU Evaluation unit class.
  * @tparam ES Evaluation set class.
  * @tparam ER Evaluation result class.
  * @group Evaluator
  */
// @deprecated("Use Evaluation instead.", "0.9.0")
// abstract class Evaluator[EI, Q, P, A, EU: ClassTag, ES, ER <: AnyRef : ClassTag]
//   extends BaseEvaluator[EI, Q, P, A, ER] {
//   type EX = Int
//   @transient private lazy val logger = Logger[this.type]
//
//   def evaluateBase(sc: SparkContext, evalDataSet: Seq[(EI, RDD[(Q, P, A)])])
//   : ER = {
//     logger.info(s"Evaluator. EvalData Set Count: ${evalDataSet.size}")
//     val evalSetResult: Seq[RDD[(EX, EI, ES)]] = evalDataSet
//     .zipWithIndex  // Need to inject an index to keep the order stable.
//     .map { case (ed, ex) => {
//       val (ei, qpaRDD) = ed
//
//       val euRDD: RDD[EU] = qpaRDD.map { case(q, p, a) => evaluateUnit(q, p, a) }
//       val esRDD: RDD[(EX, EI, ES)] = euRDD
//         .coalesce(numPartitions = 1, shuffle = true)
//         .glom()
//         .map { eus => (ex, ei, evaluateSet(ei, eus)) }
//       assert (esRDD.partitions.size == 1)
//       esRDD
//     }}
//
//     val evalSets: RDD[(EX, EI, ES)] = sc.union(evalSetResult)
//     val erRDD: RDD[ER] = evalSets
//       .coalesce(numPartitions = 1, shuffle = true)
//       .glom()
//       .map { a => {
//         val sorted: Seq[(EI, ES)] = a.sortBy(_._1).map(e => (e._2, e._3))
//         evaluateAll(sorted)
//       }}
//
//     logger.info(s"Evaluator completed.")
//
//     val er = erRDD.first
//     er
//   }
//
//   /** Implement this method to calculate a unit of evaluation, comparing a pair
//     * of predicted and actual values.
//     *
//     * @param query Input query that produced the prediction.
//     * @param prediction The predicted value.
//     * @param actual The actual value.
//     */
//   def evaluateUnit(query: Q, prediction: P, actual: A): EU
//
//   /** Implement this method to calculate an overall result of an evaluation set.
//     *
//     * @param evalInfo Evaluation Info that is related to this evaluation.
//     * @param evaluationUnits A list of evaluation units from [[evaluateUnit]].
//     */
//   def evaluateSet(evalInfo: EI, evaluationUnits: Seq[EU]): ES
//
//   /** Implement this method to aggregate all evaluation result set generated by
//     * each evaluation's [[evaluateSet]] to produce the final result.
//     *
//     * @param input A list of data parameters and evaluation set pairs to aggregate.
//     */
//   def evaluateAll(input: Seq[(EI, ES)]): ER
// }

/** Trait for nice evaluator results
  *
  * evaluator result can be rendered nicely by implementing toHTML and toJSON
  * methods. These results are rendered through dashboard.
  * @group Evaluator
  */
@deprecated("Use Evaluation instead.", "0.9.0")
trait NiceRendering {
  /** HTML portion of the rendered evaluator results. */
  def toHTML(): String

  /** JSON portion of the rendered evaluator results. */
  def toJSON(): String
}

/** An implementation of mean square error evaluator. `DP` is `AnyRef`. This
  * support any kind of data parameters.
  *
  * @group Evaluator
  */
/*
@deprecated("Use Evaluation instead.", "0.9.0")
class MeanSquareError extends Evaluator[AnyRef,
    AnyRef, Double, Double, (Double, Double), String, String] {
  def evaluateUnit(q: AnyRef, p: Double, a: Double): (Double, Double) = (p, a)

  def evaluateSet(ep: AnyRef, data: Seq[(Double, Double)]): String = {
    val units = data.map(e => math.pow(e._1 - e._2, 2))
    val mse = units.sum / units.length
    f"Set: $ep Size: ${data.length} MSE: ${mse}%8.6f"
  }

  def evaluateAll(input: Seq[(AnyRef, String)]): String = {
    input.map(_._2).mkString("\n")
  }
}
*/