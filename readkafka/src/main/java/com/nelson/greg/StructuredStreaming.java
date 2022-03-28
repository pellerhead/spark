package com.nelson.greg;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

public class StructuredStreaming {

    public static void main(String[] args) throws StreamingQueryException, TimeoutException  {

        System.setProperty("hadoop.home.dir", "./hadoop");

        Logger.getLogger("org.apache").setLevel(Level.WARN);
        Logger.getLogger("org.apache.spark.storage").setLevel(Level.ERROR);

        SparkSession session = SparkSession
            .builder()
            .appName("StructuredStreaming")
            .master("local[*]")
            .getOrCreate();

        Dataset<Row> df = session
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "trackingorders")
                .load();

        df.createOrReplaceTempView("trackingorders");

        Dataset<Row> results = session.sql("select value from trackingorders");

        StreamingQuery query = results
            .writeStream()
            .format("console")
            .outputMode(OutputMode.Append())
            .start();
        
        query.awaitTermination();
        
    }
}