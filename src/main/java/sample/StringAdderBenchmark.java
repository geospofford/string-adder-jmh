package sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StringAdderBenchmark {
    static final int nPairs = 300;

    @State(Scope.Benchmark)
    public static class PairProvider {
        static class NumberPair {
            String a, b;

            public NumberPair(String a, String b) {
                this.a = a;
                this.b = b;
            }
        }

        final List<NumberPair> pairs;

        public PairProvider() {
            List<NumberPair> pairs = new ArrayList<>(nPairs);
            Random rnd = new Random(3125L);
            for (int i = 0; i < nPairs; ++i) {
                pairs.add(
                        new NumberPair(
                                Long.toString(rnd.nextLong()),
                                Long.toString(rnd.nextLong())
                        )
                );
            }
            this.pairs = pairs;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void add1(PairProvider pairProvider) {
        for (PairProvider.NumberPair pair : pairProvider.pairs) {
            StringAdder.add1(pair.a, pair.b);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void add2(PairProvider pairProvider) {
        for (PairProvider.NumberPair pair : pairProvider.pairs) {
            StringAdder.add2(pair.a, pair.b);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void add3(PairProvider pairProvider) {
        for (PairProvider.NumberPair pair : pairProvider.pairs) {
            StringAdder.add3(pair.a, pair.b);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringAdderBenchmark.class.getSimpleName())
                .warmupIterations(100)
                .measurementIterations(100)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
