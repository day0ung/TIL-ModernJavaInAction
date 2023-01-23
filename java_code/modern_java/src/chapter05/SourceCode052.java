package chapter05;

import chapter04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SourceCode052 {
    public static void main(String[] args) {
        List<Dish> specialMenu = Dish.getMenu();

        List<Dish> filteredMenu
                = specialMenu.stream()
                  .filter(dish -> {
                      System.out.println("filter" + dish.getCalories());
                      return dish.getCalories() < 320;
                  }) //필터링한 카테고리명 출력
                  .collect(Collectors.toList());
        filteredMenu.forEach( d -> {
            System.out.println(d.getCalories());
        });
        //위 List는 이미 칼로리 순으로 정렬되어있다. (filter연산을 이용하면 전체 스트림을 반복하면서 각 요소에 predicate를 적용한다)


        //takeWhile 사용   java 9 버전
        List<Dish> sliceMenu1
                = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(Collectors.toList());

        sliceMenu1.forEach( d -> {
            System.out.println(d.getCalories());
        });


    }
}
