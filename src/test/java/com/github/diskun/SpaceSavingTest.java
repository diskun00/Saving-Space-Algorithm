package com.github.diskun;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.*;

/**
 * Created by Diskun on Nov 2019
 */
public class SpaceSavingTest {

    private void init(){
        SpaceSaving<String> list = new SpaceSaving<String>(5);
        String sample = "a b b c c c e e e e e d d d d g g g g g g g f f f f f f";

    }

    @Test
    public void readItem() {

        SpaceSaving<String> spaceSaving = new SpaceSaving<String>(5);
        String sample = "a b b c c c e e e e e d d d d g g g g g g g f f f f f f";
        List<String> data = Arrays.asList(sample.split(" "));
        for (String datum : data) {
            spaceSaving.readItem(datum);

        }
        System.out.println(spaceSaving.getTopK(3));

    }

    @Test
    public void getTopK() {

    }

    @Test
    public void main() {
        int k = 15;
        float epsilon = 0.001f;
        SpaceSaving<String> spaceSaving = new SpaceSaving<>(epsilon);
        String fileName = "/Users/diskun/Work/Workspace/Java/SpaceSavingAlgorithm/src/main/resources/dataset.csv";
        //read file into stream, try-with-resources
        List<Map.Entry<String, Long>> topKOfStream = null;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            topKOfStream = stream
                    .map(item -> {
                        spaceSaving.readItem(item);
                        return item;
                    })
                    .collect(groupingBy(Function.identity(), counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(k)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> topKOfAlgo = spaceSaving.getTopK(k);
        System.out.println("entries = " + topKOfStream);
        System.out.println(topKOfAlgo);
        System.out.println("result"+'\t'+"space saving"+'\t'+"word count");
        for (int i = 0; i < k; i++) {
            String k1 = topKOfAlgo.get(i);
            String k2 = topKOfStream.get(i).getKey();
            System.out.println(String.valueOf(k1.equals(k2)) + '\t' + k1 +"\t|\t"+ k2);
        }
    }
}