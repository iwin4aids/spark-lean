#!/usr/bin/env bash
set HADOOP_USER_NAME=root
spark-submit \
--class com.skloda.spark.WordCountLocal \
--master spark://localhost:7077 \
--name WordCount001 \
./target/spark-learn-1.0-SNAPSHOT.jar \
hdfs://192.168.144.180:9000/input hdfs://192.168.144.180:9000/output

#spark-submit --class org.apache.spark.examples.SparkPi \
#--master spark://localhost:7077 \
#--deploy-mode cluster \
#--driver-memory 1g \
#--executor-memory 1g \
#--executor-cores 1 \
#/usr/local/Cellar/apache-spark/2.4.3/libexec/examples/jars/spark-examples_2.11-2.4.3.jar \
#10
