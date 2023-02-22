package chapter06;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;
import static java.util.stream.Collectors.partitioningBy;

public class SourceCode066 {
    //커스텀 컬렉터를 구현해서 성능개선하기
    public static void main(String ... args) {

        System.out.println("Partitioning done in: " + execute(PartitionPrimeNumbers::partitionPrimesWithCustomCollector) + " msecs");
    }
    //컬렉터 성능비교
    private static long execute(Consumer<Integer> primePartitioner) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            primePartitioner.accept(1_000_000); //백만개의 숫자를 소수와 비소수로 분할
            long duration = (System.nanoTime() - start) / 1_000_000; //밀리초단위로 측정
            if (duration < fastest) {
                fastest = duration; //가장 빨리 실행되었는지 확인
            }
            System.out.println("done in " + duration);
        }
        return fastest;
    }


    //1단계: Collector 클래스 시그니처 정의
    public static class PrimeNumbersCollector
            implements Collector<Integer, //스트림 요소의 형식
            Map<Boolean, List<Integer>>,  //누적자 형식
            Map<Boolean, List<Integer>>>  //수집 연산의 결과 형식
    {

        //======2단계 리듀싱 연산구현======
        //누적자를 만드는 함수 반환 true,false키와 빈 리스트로 초기화 하며 수집과정에서 빈 리스트에 각각 소수와 빈 소수를 추가
        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> new HashMap<>() {{
                put(true, new ArrayList<Integer>());
                put(false, new ArrayList<Integer>());
            }};
        }

        //지금까지 발견한 소수리스트(누적 맵의 true키로 이들값에 접근할수 있다.)
        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
                acc.get(isPrime(acc.get(true), candidate)) // ismPrime결과에 따라 소수리스트와 비소수 리스트를 만든다
                        .add(candidate); //candidate를 알맞은 리스트에 추가
            };
        }
        //======3단계 병렬 실행할 수 있는 컬렉터 만들기(가능하다면)======
        //combiner메서드는 호출될일이 없다.(알고리즘 자체가 순차적이여서 컬렉터를 실제 병렬로 사용할수 없다.)
        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) -> { // 두번째맵을 첫번째 맵에 병합
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
                return map1;
            };
        }

        //======4단계 finisher 메서드와 컬렉터의 characteristics메서드======
        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity(); //최종 수집과정에서 데이터 변환이 필요하지 않으므로 항등함수 반환
        }

        //커스텀컬렉터는 CONCURRENT, UNORDERED도 아니다. 발견한 소수의 순서에 의미가 있으므로 컬렉터는 IDENTITY_FINISH
        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
        }


        public static boolean isPrime(List<Integer> primes, Integer candidate) {
            double candidateRoot = Math.sqrt(candidate);
            //return takeWhile(primes, i -> i <= candidateRoot).stream().noneMatch(i -> candidate % i == 0);
            return primes.stream().takeWhile(i -> i <= candidateRoot).noneMatch(i -> candidate % i == 0);
        }

    }



    static class PartitionPrimeNumbers{
        public static Map<Boolean, List<Integer>> partitionPrimes(int n) {
            return IntStream.rangeClosed(2, n).boxed()
                    .collect(partitioningBy(candidate -> isPrime(candidate)));
        }

        public static boolean isPrime(int candidate) {
            return IntStream.rangeClosed(2, candidate-1)
                    .limit((long) Math.floor(Math.sqrt(candidate)) - 1)
                    .noneMatch(i -> candidate % i == 0);
        }

        public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
            return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
        }


    }


}
