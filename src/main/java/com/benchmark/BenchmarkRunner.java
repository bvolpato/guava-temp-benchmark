package com.benchmark;

public class BenchmarkRunner {
    public static void main(String[] args) {
        System.out.println("Benchmark project created successfully!");
        System.out.println("To run the benchmark, use: ./gradlew jmh");
        System.out.println("This will compare the performance of:");
        System.out.println("1. Stream-based implementation (toIntList)");
        System.out.println("2. Guava-based implementation (toUnmodifiableIntList)");
        System.out.println("The benchmark will test arrays of sizes: 10, 100, 1000, 10000");
    }
}