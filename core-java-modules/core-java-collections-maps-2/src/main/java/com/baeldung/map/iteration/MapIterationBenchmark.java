package com.baeldung.map.iteration;

import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.map.HashedMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 10, timeUnit = TimeUnit.MICROSECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.MICROSECONDS)
public class MapIterationBenchmark {

    private static final int MAP_SIZE = 1000000;
    MapIteration mapIteration = new MapIteration();
    Map<Integer, Integer> map;
    IterableMap<Integer, Integer> iterableMap;
    MutableMap<Integer, Integer> mutableMap;

    @Param({"100","1000","10000","100000","1000000"})
    public int size;

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder().include(MapIterationBenchmark.class.getSimpleName())
            .param("size", "100", "1000", "10000", "100000", "1000000")
            .forks(1)
            .build();
        new Runner(opt).run();

    }

    @Setup(Level.Trial)
    public void setup() {
        map = new HashMap<>();
        iterableMap = new HashedMap<>();
        mutableMap = UnifiedMap.newMap();
        for (int i = 0; i < size; i++) {
            map.put(i, i);
            iterableMap.put(i, i);
            mutableMap.put(i, i);
        }
    }

    @Benchmark
    public long benchmarkIterateUsingEntrySet() {
        return mapIteration.iterateUsingEntrySet(map);
    }

    @Benchmark
    public long benchmarkIterateUsingLambda() {
        return mapIteration.iterateUsingLambda(map);
    }

    @Benchmark
    public long benchmarkIterateByKeysUsingLambda() {
        return mapIteration.iterateByKeysUsingLambda(map);
    }

    @Benchmark
    public long benchmarkIterateValuesUsingLambda() {
        return mapIteration.iterateValuesUsingLambda(map);
    }

    @Benchmark
    public long benchmarkIterateUsingIteratorAndEntry() {
        return mapIteration.iterateUsingIteratorAndEntry(map);
    }

    @Benchmark
    public long benchmarkIterateUsingIteratorAndKeySet() {
        return mapIteration.iterateUsingIteratorAndKeySet(map);
    }

    @Benchmark
    public long benchmarkIterateUsingKeySetAndForeach() {
        return mapIteration.iterateUsingKeySetAndForeach(map);
    }

    @Benchmark
    public long benchmarkIterateUsingStreamAPI() throws IOException {
        return mapIteration.iterateMapUsingParallelStreamApiParallel(map);
    }

    @Benchmark
    public long benchmarkIterateUsingStreamAPIAndEntrySet() throws IOException {
        return mapIteration.iterateUsingStreamAPIAndEntrySet(map);
    }

    @Benchmark
    public long benchmarkIterateUsingStreamAPIAndKeySet() throws IOException {
        return mapIteration.iterateUsingStreamAPIAndKeySet(map);
    }

    @Benchmark
    public long benchmarkIterateUsingMapIteratorApacheCollection() {
        return mapIteration.iterateUsingMapIteratorApacheCollection(iterableMap);
    }

    @Benchmark
    public long benchmarkIterateEclipseMap() throws IOException {
        return mapIteration.iterateEclipseMap(mutableMap);
    }
}