# Find Most Frequent K-mers

This Spark application performs one of the most fundamental operations in genomic sequencig. It ingests a FASTQ file and outputs the top N most frequent k-mers in a genomic sequence to standard output.

## Requirements
* Spark 1.1.0
* Scala 2.10.4
* [sbt](scala-sbt.org)

## Instructions
* Compile and build the JAR file after you've cloned this Git repository.
```shell
sbt package
```
* Run it in your Spark cluster. Be sure to include the input file path, k, and N as argument parameters. In the below example, the sample FASTQ file included in this Git repo is used and the top 5 trimers are being looked for.
```shell
$SPARK-1.1.0_HOME/bin/spark-submit --class "MostFrequentKMers" --master local[4] target/scala-2.10/mostfrequentkmers_2.10-1.0.jar sample.fastq 3 5
```
