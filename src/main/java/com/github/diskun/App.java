package com.github.diskun;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Hello world!
 */
public class App {
    /**
     * Entry point to Space Saving Program
     * @param args parameters k,N; k: top k to count; N: number of lines to read, -1 means to read the whole file
     */
    public static void main(String[] args) {
        //Any element with (frequency - error) > epsilon * N is guaranteed to be in the result
        float epsilon = 0.001f;
        String fileName = "src/main/resources/dataset.csv";
        int parameterSize = 2;
        if (args.length != parameterSize) {
            throw new IllegalArgumentException("Please pass parameter K and N to the program. " +
                    "E.g: 10 1000 to count top10 for first 1000 domains in the file ");
        }
        Integer k = Integer.valueOf(args[0]);
        Integer n = Integer.valueOf(args[1]);
        if (k < 0 || n < -1 || (k > n && n>0) ) {
            throw new IllegalArgumentException("Parameter is not valid. N >= k > 0 or N=-1 means read all lines.");
        }

        SpaceSaving<String> spaceSaving = new SpaceSaving<>(epsilon);
        //read file into stream
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            Stream<String> streamToProcess = stream;
            if(n!=-1){
                streamToProcess = stream.limit(n);
            }
            streamToProcess.forEach(spaceSaving::readItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> topKOfAlgo = spaceSaving.getTopK(k);
        System.out.println("topKOfAlgo = " + topKOfAlgo);
    }
}
