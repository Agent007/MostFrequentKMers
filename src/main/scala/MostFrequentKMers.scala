import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._

import scala.collection.mutable

object MostFrequentKMers {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Most Frequent k-mers")
    val sc = new SparkContext(conf)
    val inputFile = args(0)
    val k = args(1).toInt
    val n = args(2).toInt
    val text = sc.textFile(inputFile)
    val filteredText = text.filter(line => "^[@+;!~]".r.findFirstIn(line) match {
      case None => true
      case Some(_) => false
    })
    filteredText.cache();
    //filteredText.foreach(line => println(line))
    val kMers = filteredText.flatMap { line =>
      var kMersInThisLine = new mutable.Queue[String]
      val lineLength = line.size
      var i = 0
      for (i <- 0 to lineLength - k) {
        kMersInThisLine += line.substring(i, i + k)
      }
      //println("kMersInThisLine: %s".format(kMersInThisLine.length))
      kMersInThisLine
    }
    //println("kMers: %s".format(kMers.count()))
    //kMers.foreach(kMer => println(kMer))
    val kMerCounts = kMers.map(kMer => (kMer, 1)).reduceByKey(_ + _)
    //kMerCounts.foreach(kMerCount => println(kMerCount))
    val mostFrequentKMers = kMerCounts.takeOrdered(n)(Ordering.by[(String, Int), Int](_._2).reverse)
    mostFrequentKMers.foreach(kMerCount => println(kMerCount))
  }
}
