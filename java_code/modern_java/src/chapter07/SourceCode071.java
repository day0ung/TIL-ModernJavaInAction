package chapter07;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime) //벤치마크 대상 메서드를 실행하는데 걸린 평균시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS) //벤치마크 결과를 밀리초 단위로 출력
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"}) //4Gb의 힙공간을 제공한 환경에서 두번 벤치마크를 수행해 결과의 신뢰성 확보
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

    @TearDown(Level.Invocation) //매번 벤치마크를 실행한 다음에는 가비지 컬렉터 동작시도
    public void tearDown(){
        System.gc();
    }
}
