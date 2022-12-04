package chapter03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SourceCode034 {
    public static void main(String[] args)  {
        //Predicate (String 객체를 인수로받는 람다 예제)
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> listOfThins = new ArrayList<>();
        List<String> nonEmpty = filter(listOfThins, nonEmptyStringPredicate);

        //Consumer ( forEach와 람다를 이용해서 리스트의 모든 항목을 출력하는 예제)
        forEach(
                Arrays.asList(1,2,3,4,5) ,
                (Integer i ) -> System.out.println(i)   //<- Consumer의 accept메서드를 구현하는 람다
        );

        //Function (String 리스트를 인수로 받아, 각 String의 길이를 포함하는 Integer 리스트로 반환하는 map메서드 정의예제)

        List<Integer> l = map(
                Arrays.asList("lambdas", "in", "action"),
                (String s) -> s.length() // Function의 apply메서드를 구현하는 람다
        );
        System.out.println(l); //[7,2,6]

    }
    //Predicate
    @FunctionalInterface
    public interface Predicate<T>{
        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p ){
        List<T> results = new ArrayList<>();
        for (T t: list){
            if(p.test(t)){
                results.add(t);
            }
        }
        return results;
    }
    //=========

    //Consumer
    @FunctionalInterface
    public interface Consumer<T>{
        void accept(T t);
    }

    public static <T> void forEach(List<T> list, Consumer<T> c ){
        for (T t: list){
            c.accept(t);
        }

    }
    //==========

    @FunctionalInterface
    public interface Function<T, R>{
        R apply(T t);
    }

    public static <T, R> List<R> map(List<T> list, Function<T,R> f ){
        List<R> result = new ArrayList<>();
        for (T t : list){
            result.add(f.apply(t));
        }
        return result;
    }

}
