package chapter06;

import chapter04.Dish;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class SourceCode063 {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();

        //메뉴 그룹화
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));

        //6.3.1 그룹화된 요소 조작 (500칼로리가 넘는 요리만 필터
        Map<Dish.Type, List<Dish>> caloricDishesByType =
                menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        System.out.println(caloricDishesByType);
        //문제점! FISH는 필터에서 사라자기 때문에 키 자체가 사라진다

        //보완 groupingBy(value, filtering(), return )
        Map<Dish.Type, List<Dish>> caloricDishesByType2 =
                menu.stream().collect(groupingBy(Dish::getType,
                                    filtering(dish -> dish.getCalories() > 500, toList() )));
        System.out.println(caloricDishesByType2);

        //맵핑함수 활용 groupingBy(value, mapping(), return)
        Map<Dish.Type, List<String>> dishesNameByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                mapping(Dish::getName, toList())));
        System.out.println(dishesNameByType);



    }
}
