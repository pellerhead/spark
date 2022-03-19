package com.nelson.greg;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Main {

    public static void main(String[] args) {

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        List<Integer> inputData = new ArrayList<>();
        
        inputData.add(35);
        inputData.add(12);
        inputData.add(90);
        inputData.add(20);

        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<Integer> myRDD = sc.parallelize(inputData);

        Integer result = myRDD.reduce((value1, value2) -> value1 + value2);

        System.out.println(result);

        JavaRDD<Double> sqrtRDD = myRDD.map(Math::sqrt);

        sqrtRDD.foreach(value -> System.out.println(value));

        System.out.println(sqrtRDD.count());

        JavaRDD<Long> singleIntegerRdd = sqrtRDD.map( value -> 1L);
        Long count = singleIntegerRdd.reduce((value1, value2) -> value1 + value2);
        System.out.println(count);

        sc.close();
    }
}