package chapter09;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SourceCode094 {
    public static void main(String[] args) {
        //debugging
//        List<SourceCode093.Point> points= Arrays.asList(new SourceCode093.Point(12,2), null);
//        points.stream().map(p -> p.getX()).forEach(System.out::println);

        /*
        아래 에러가 출력된다.
        Exception in thread "main" java.lang.NullPointerException
            at chapter09.SourceCode094.lambda$main$0(SourceCode094.java:10) // $0는 무슨의미??
            at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:195)
            at java.base/java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
            ...
          이와같은 문자는 람다 표현식내부에서 에러가 발생했음을 가리킨다.
         */


        //정보로깅
        List<Integer> numbers = Arrays.asList(2,3,4,5);
        numbers.stream()
                .map(x -> x+ 17)
                .filter(x -> x%2 ==0)
                .limit(3)
                .forEach(System.out::println); //20 22 출력
        //위코드는 forEach를 호출하는 순간 전체 스트림이 소비된다. 각각의 연산(map, filter,limit)이 어떤결과를 도출하는지 확인할수 있으면 좋다.

        //peek 으로 스트림 파이프라인을 흐르는 값 확인
        numbers.stream()
                .peek(x -> System.out.println( "from stream" + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map" + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter" + x))
                .limit(3)
                .peek(x -> System.out.println("after limit" + x))
                .collect(toList());


    }
}
