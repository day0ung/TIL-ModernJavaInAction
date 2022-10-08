package chapter01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SourceCode011 {
    //'사과목록을 무게순으로 정렬하는 고전적 코드'를 예시로 들었는데 한번 실행해보자

    public static void main(String[] args){
        ArrayList<Apple> apples = new ArrayList<>();
        apples.add(new Apple(3));
        apples.add(new Apple(9));
        apples.add(new Apple(2));


        /*기존 정렬시 사용하는 코드*/
        Collections.sort(apples, new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWegiht() - a2.getWegiht();
            }
        });

        /*자바8을 이용한 코드*/
        apples.sort(Comparator.comparing(Apple::getWegiht)); //책과 코드가 동일하지 않지만 Comparator를 앞에 붙여주었다.


        for (Apple apple: apples) {
            System.out.println(apple.getWegiht());
        }

        /*
        모던자바인 액션에 나온대로 코드실행시 에러가 발생..원인은아직 못찾았다
        Collections.sort(apples, new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2) {
                return a1.getWegiht().compareTo(a2.getWegiht());
            }
        });

         */


    }

}
