import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Anagram {
    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        private Text sortedWord = new Text();
        private Text word = new Text();

        private static String sortStringAlphabetically(String inputString) {
            char[] tempCharArray = inputString.toCharArray();
            Arrays.sort(tempCharArray);
            return new String(tempCharArray);
        }

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            word.set(line);
            sortedWord.set(sortStringAlphabetically(line));
            context.write(sortedWord, word);
        }
    }

    public static class Reduce extends Reducer<Text, Text, LongWritable, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context)
                throws IOException, InterruptedException {
            Iterator<Text> iterator = values.iterator();

            long totalAnagrams = 1;
            StringBuilder anagrams = new StringBuilder(iterator.next().toString());

            while (iterator.hasNext()) {
                anagrams.append(",").append(iterator.next().toString());
                totalAnagrams++;
            }

            if (totalAnagrams > 1)
                context.write(new LongWritable(totalAnagrams), new Text(anagrams.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "anagram");
        job.setJarByClass(Anagram.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path("hdfs://localhost:54310/user/csdeptucy/input/words"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://localhost:54310/user/csdeptucy/output"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
