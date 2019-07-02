package com.skloda.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 集群模式运行word count
  * 通过参数给出input和output路径（hdfs)
  * 注意，写hdfs需要对应权限，设置环境变量HADOOP_USER_NAME=root
  */
object WordCountCluster {

  def main(args: Array[String]): Unit = {

    //1、创建sparkConf对象,设置appName和master的地址。集群模式 不使用 setMaster("local[2]")
    val sparkConf = new SparkConf().setAppName("WordCountCluster")

    //2、创建spark context对象
    val sc = new SparkContext(sparkConf)

    //设置日志输出级别
    sc.setLogLevel("WARN")

    // 3、读取数据文件
    val data: RDD[String] = sc.textFile(args(0))

    //4、切分文件中的每一行,返回文件所有单词。flatMap(_.split(" ")) 表示每一行数据以空格切分
    val words: RDD[String] = data.flatMap(_.split(" "))

    //5、每个单词记为1，(单词，1)。map((_,1))表示每个单词封装为一个元祖，其key为单词，value为1
    val wordAndOne: RDD[(String, Int)] = words.map((_, 1))

    //6、相同单词出现的次数累加。reduceByKey(_+_)表示对相同的key(单词)对应的value进行累加计算
    val result: RDD[(String, Int)] = wordAndOne.reduceByKey(_ + _)

    //按照单词出现的次数降序排列：sortBy(_._2, false)。 _._2 的格式为 _._下标值，表示遍历出来的每个元祖的同时，取出每个元祖中的第二个元素
    //按照单词出现的次数升序排列：sortBy(_._2) 	      false 表示 降序 排序
    val sortResult: RDD[(String, Int)] = result.sortBy(_._2, false)

    //7、结果数据保存在HDFS上
    sortResult.saveAsTextFile(args(1))

    //8、关闭sc
    sc.stop()
  }
}
