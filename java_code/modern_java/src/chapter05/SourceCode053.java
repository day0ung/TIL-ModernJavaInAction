package chapter05;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class SourceCode053 {

    public static void main(String[] args) {
        /**
         5.3.1 스트림의 각요소에 함수 적용하기
        */
        List<String> words = Arrays.asList("Modern", "java", "in", "action");
        List<Integer> wordLengths = words.stream()
                                    .map(String::length)
                                    .collect(toList());
        wordLengths.forEach(s -> {
            System.out.println(s);
        });

        //요리명의 길이
        List<Dish> menu = Dish.getMenu();
        List<Integer> dishNameLengths = menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());

        dishNameLengths.forEach(s -> {
            System.out.print(s);
        });

        /**
         5.3.2 스트림 평면화
         */

        List<String> helloworld = Arrays.asList("Hello", "World");
        //아래코드는 에러가 발생한다. 반환타입: List<String[]> 이다
        /**
            List<String> example = helloworld.stream()
                    .map(word -> word.split(""))
                    .distinct()
                    .collect(toList());
        */
        // 문제해결방법
        /** map과 Arrays.stream활용 (문자열을 받아 스트림을 만드는 메서드) */
        String[] arraysOfwords = {"Goodbye", "World"};
        Stream<String> streamOfwords = Arrays.stream(arraysOfwords);

//        List<String> example = helloworld.stream()
//                .map(word -> word.split(""))
//                .map(Arrays::stream)
//                .distinct()
//                .collect(toList());
        //결국 위 코드도 스트림 리스트 List<Stream<String>> 가 만들어지면서 error 발생

        /** flatmap활용 */
        List<String> example2 = helloworld.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        example2.forEach( s -> { System.out.print(s);}); //HeloWrd


    }
}
