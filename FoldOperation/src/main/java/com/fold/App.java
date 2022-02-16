package com.fold;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.FoldFunction;
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

    DataStream < Tuple5 < String, String, String, Integer, Integer >> folded = mapped.keyBy(0).fold(new Tuple4<String,String,Integer,Integer>("","",0,0),new FoldFunction1());
    DataStream < Tuple2 < String, Double >> profitPerMonth =
      folded.map(new MapFunction < Tuple4 < String, String, Integer, Integer > , Tuple2 < String, Double >> () {
        public Tuple2 < String, Double > map(Tuple5 < String, String, String, Integer, Integer > input) {
          return new Tuple2 < String, Double > (input.f0, new Double((input.f2 * 1.0) / input.f3));
        }
      });

      profitPerMonth.writeAsText("/shared/profit_per_month.txt");
      env.execute("Fold Operation");
    }

    public static class FoldFunction1 implements FoldFunction < Tuple5 < String, String, String, Integer, Integer >> {
        public Tuple4 <String,String,Integer,Integer >
        fold(Tuple4 < String, String, Integer, Integer > defaultIn,
          Tuple5 < String, String, String, Integer, Integer > curr) {
              defaultIn.f0 =curr.f0;
              defaultIn.f1 = curr.f1;
              defaultIn.f2 += curr.f3;
              defaultIn.f3 += curr.f4;
              return defaultIn;
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
