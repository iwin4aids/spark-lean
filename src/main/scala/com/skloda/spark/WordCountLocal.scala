package com.skloda.spark

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}

/**
  * scala中没有静态方法和变量
  * 使用object实现单例和静态方法
  * 等同java的单例静态工具类
  */
object WordCountLocal {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setAppName("WordCountLocal")
    conf.setMaster("local")

    val sparkContext = new SparkContext(conf)

    // word count简化写法
    sparkContext.textFile(args(0)).flatMap(_.split(" "))
      .map((_,1)).reduceByKey(_+_, 1)
      .sortBy(_._2, false).saveAsTextFile(args(1))

//    // 复杂写法
//    val textFileRDD = sparkContext.textFile("src/main/resources/word.txt")
//    val wordRDD = textFileRDD.flatMap(line => line.split(" "))
//    val pairWordRDD = wordRDD.map(word => (word, 1))
//    val wordCountRDD = pairWordRDD.reduceByKey((a, b) => a + b)
//    // 思路key和value反转，按key默认排序，然后转回来
//    val sortWords = wordCountRDD.map(x => (x._2,x._1)).sortByKey(false).map(x => (x._2,x._1))
//    deleteDir(new File("src/main/resources/result"))
//    // 打印结果
//    sortWords.foreach(println)
    // 保存到本地文件中
    // sortWords.saveAsTextFile("src/main/resources/result")
  }

  /**
    * 删除目录
    *
    * @param dir
    */
  def deleteDir(dir: File): Unit = {
    if(!dir.exists())
      return
    val files = dir.listFiles()
    files.foreach(f => {
      if (f.isDirectory) {
        deleteDir(f)
      } else {
        f.delete()
        println("delete file " + f.getAbsolutePath)
      }
    })
    dir.delete()
    println("delete dir " + dir.getAbsolutePath)
  }

}
