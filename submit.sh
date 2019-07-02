# 这里使用spark standalone模式，使用spark master来统一管理资源
#!/usr/bin/env bash
spark-submit \
--class com.skloda.spark.WordCountCluster \
--master spark://localhost:7077 \
--name WordCountCluster \
./target/spark-learn-1.0-SNAPSHOT.jar \
hdfs://192.168.144.180:9000/input/word_count.txt hdfs://192.168.144.180:9000/output
