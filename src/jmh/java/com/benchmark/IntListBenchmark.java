package com.benchmark;

import com.google.common.primitives.Ints;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@BenchmarkMode({ Mode.Throughput, Mode.AverageTime })
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 1, time = 500, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 500, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class IntListBenchmark {

    @Param({ "10", "1000", "100000", "1000000" })
    private int arraySize;

    private int[] testArray;

    @Setup
    public void setup() {
        testArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            testArray[i] = i;
        }
    }

    @Benchmark
    public void streamImplementation(Blackhole bh) {
        List<Integer> result = toIntList(testArray);
        bh.consume(result);
    }

    @Benchmark
    public void guavaImplementation(Blackhole bh) {
        List<Integer> result = toUnmodifiableIntList(testArray);
        bh.consume(result);
    }

    public static List<Integer> toIntList(int[] ints) {
        if (ints != null) {
            return IntStream.of(ints).boxed().collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public static List<Integer> toUnmodifiableIntList(int[] ints) {
        if (ints != null) {
            return Collections.unmodifiableList(Ints.asList(ints));
        } else {
            return null;
        }
    }
}