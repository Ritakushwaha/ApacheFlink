package com.datastream;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        // set up the streaming execution environment
    final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStream <String> data = env.readTextFile("/home/rita/Documents/ApacheFlink/AverageProfit/avg");
    DataStream < Tuple5 < String, String, String, Integer, Integer >> mapped = data.map(new Splitter());

    DataStream < Tuple5 < String, String, String, Integer, Integer >> reduced = mapped.keyBy(t -> t.f0).reduce(new Reduce1());
    DataStream < Tuple2 < String, Double >> profitPerMonth =
      reduced.map(new MapFunction < Tuple5 < String, String, String, Integer, Integer > , Tuple2 < String, Double >> () {
        public Tuple2 < String, Double > map(Tuple5 < String, String, String, Integer, Integer > input) {
          return new Tuple2 < String, Double > (input.f0, new Double((input.f3 * 1.0) / input.f4));
        }
      });

      profitPerMonth.writeAsText("/shared/profit_per_month.txt");
      env.execute("Avg Profit Per Month");
    }

    public static class Reduce1 implements ReduceFunction < Tuple5 < String, String, String, Integer, Integer >> {
        public Tuple5 < String,
        String,
        String,
        Integer,
        Integer >
        reduce(Tuple5 < String, String, String, Integer, Integer > current,
          Tuple5 < String, String, String, Integer, Integer > pre_result) {
          return new Tuple5 < String, String, String, Integer, Integer > (current.f0,
            current.f1,
            current.f2,
            current.f3 + pre_result.f3,
            current.f4 + pre_result.f4);
        }
      }

      public static class Splitter implements MapFunction < String, Tuple5 < String, String, String, Integer, Integer >> {
        public Tuple5<String,String,String,Integer,Integer> map(String value)
        {
          String[] words = value.split(",");
          return new Tuple5 < String, String, String, Integer, Integer > (words[1],words[2],words[3],Integer.parseInt(words[4]),1);
        } 
      }
}
