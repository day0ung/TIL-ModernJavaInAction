package chapter07;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime) //벤치마크 대상 메서드를 실행하는데 걸린 평균시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS) //벤치마크 결과를 밀리초 단위로 출력
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"}) //4Gb의 힙공간을 제공한 환경에서 두번 벤치마크를 수행해 결과의 신뢰성 확보
@Measurement(iterations = 2)
@Warmup(iterations = 3)
public class SourceCode071 {
    public static void main(String[] args) {
        long test = SourceCode071.sequentialSum();
        System.out.println(test);
    }
    private static final long N = 10_000_000L;
    @Benchmark //벤치마크 대상 메서드
    public static long sequentialSum() {
        return Stream.iterate( 1L, i -> i+1).limit(N)
                .reduce(0L, Long::sum);
    }

//    # Run complete. Total time: 00:01:22
//    Benchmark                  Mode  Cnt   Score   Error  Units
//    MyBenchmark.sequentialSum  avgt   40  **78.975** ± 0.156  ms/op

    //TearDown 을 실행하려면 해당 어노테이션 이 있어야함 -> @State(Scope.Thread)
    @TearDown(Level.Invocation) //매번 벤치마크를 실행한 다음에는 가비지 컬렉터 동작시도
    public void tearDown(){
        System.gc();
    }





    //for -loop
    @Benchmark
    public long iterativeSum() {
        long result = 0;
        for (long i = 1L; i <= N; i++) {
            result += i;
        }
        return result;
    }

//   # Run complete. Total time: 00:01:21
//    Benchmark                 Mode  Cnt  Score   Error  Units
//    MyBenchmark.iterativeSum  avgt   40  **2.870** ± 0.017  ms/op

    @Benchmark  //Stream.iterate.parallel
    public long parallelSum() {
        return Stream.iterate(1L, i -> i + 1).limit(N).parallel()
                .reduce(0L, Long::sum);
    }

    @Benchmark  //LongStream.sequential
    public long longStreamSequentialSum() {
        return LongStream.rangeClosed(1L, N)
                .reduce(0L, Long::sum);
    }

//    # Run complete. Total time: 00:01:21
//    Benchmark                            Mode  Cnt  Score   Error  Units
//    MyBenchmark.longStreamSequentialSum  avgt   40  **3.258** ± 0.018  ms/op


    @Benchmark //LongStream.parallel
    public long longStreamParallelSum() {
        return LongStream.rangeClosed(1L, N).parallel()
                .reduce(0L, Long::sum);
    }

//    # Run complete. Total time: 00:01:21
//    Benchmark                  Mode  Cnt  Score   Error  Units
//    MyBenchmark.sequentialSum  avgt   40  **2.782** ± 1.084  ms/op

}
