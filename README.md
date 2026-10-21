# Benchmark Project

A Java benchmarking project using JMH (Java Microbenchmark Harness) and Gradle.

## Prerequisites

- JDK 17 or higher to run Gradle 9.8.1. The benchmark code targets Java 11 and can run on Java 11.
- Gradle (or use the included Gradle wrapper)

## Running Benchmarks

### Using Gradle Wrapper (Recommended)

```bash
# Run all benchmarks
./gradlew jmh

# Run specific benchmark class
./gradlew jmh --include="*YourBenchmarkClass*"

# Run with custom JVM arguments
./gradlew jmh -PjvmArgs="-Xmx2g -Xms1g"
```

### Using Gradle

```bash
# Run all benchmarks
gradle jmh

# Run specific benchmark class
gradle jmh --include="*YourBenchmarkClass*"
```

## Benchmark Configuration

### Current Benchmark: IntListBenchmark

The `IntListBenchmark` class compares two approaches for converting int arrays to Integer Lists:

1. **Stream Implementation** (`toIntList`): Uses `IntStream.of(ints).boxed().collect(Collectors.toList())`
2. **Guava Implementation** (`toUnmodifiableIntList`): Uses `Collections.unmodifiableList(Ints.asList(ints))`

**Benchmark Parameters:**
- **Array Sizes**: 10, 1,000, 100,000, 1,000,000 elements
- **Mode**: Both Throughput and AverageTime measurement
- **Time Unit**: Microseconds
- **Warmup**: 1 iteration, 500 seconds each
- **Measurement**: 2 iterations, 500 seconds each  
- **Fork**: 1 JVM process

**Key Findings:**
- Guava implementation shows consistent ~1.3-1.6μs performance regardless of array size
- Stream implementation performance degrades significantly with larger arrays (8.8μs → 7.3ms)
- Guava approach is ~7-4500x faster than streams for larger datasets

## Project Structure

```
src/
├── main/java/              # Main source code
├── jmh/java/              # JMH benchmark sources
└── test/java/             # Unit tests
```

## Building the Project

```bash
# Compile the project
./gradlew build

# Run tests
./gradlew test

# Clean build artifacts
./gradlew clean
```

## Benchmark Results

Results will be displayed in the console and can be redirected to a file:

```bash
./gradlew jmh > benchmark-results.txt
```


Current results
```
Benchmark                              (arraySize)  Mode  Cnt        Score         Error  Units
IntListBenchmark.guavaImplementation            10    ss   10     1266.800 ±     199.548  ns/op
IntListBenchmark.guavaImplementation          1000    ss   10     1387.200 ±     531.909  ns/op
IntListBenchmark.guavaImplementation        100000    ss   10     1262.500 ±     188.043  ns/op
IntListBenchmark.guavaImplementation       1000000    ss   10     1600.000 ±    1509.810  ns/op
IntListBenchmark.streamImplementation           10    ss   10     8883.500 ±    1858.339  ns/op
IntListBenchmark.streamImplementation         1000    ss   10    46229.000 ±   25265.952  ns/op
IntListBenchmark.streamImplementation       100000    ss   10   736904.100 ±  299912.894  ns/op
IntListBenchmark.streamImplementation      1000000    ss   10  7321966.800 ± 7146920.166  ns/op
```

