package chapter04;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class SourceCode041 {
    //기존코드(java7) vs 최신코드(java8)

    /**
      예제) 저칼로리 요리명을 반환하고 칼로리를 기준으로 요리를 정렬하는 자바코드
     */
    public static void main(String[] args){
        List<Dish> menu = Arrays.asList(
          new Dish("pork", false, 800, Dish.Type.MEAT),
          new Dish("beef", false, 700, Dish.Type.MEAT),
          new Dish("chicken", false, 400, Dish.Type.MEAT),
          new Dish("french fries", true, 530, Dish.Type.OTHER),
          new Dish("rice", true, 350, Dish.Type.OTHER),
          new Dish("season fruit", true, 120, Dish.Type.OTHER),
          new Dish("pizza", true, 550, Dish.Type.FISH),
          new Dish("prawns", false, 300, Dish.Type.FISH),
          new Dish("salmon", false, 400, Dish.Type.FISH)
        );

        //--java7--
        List<Dish> lowCaloricDishes = new ArrayList<>();
        //누적자로 요소 필터링
        for (Dish dish : menu){
            if(dish.getCalories() < 400){
                lowCaloricDishes.add(dish);
            }
        }
        //익명클래스로 요리정렬
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        //정렬된리스트를 처리하면서 요리이름 선택
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloricDishes){
            lowCaloricDishesName.add(dish.getName());
        }

        System.out.println(lowCaloricDishesName);

        /**
          위 코드에서는 lowCaloricDishes라는 '가비지 변수'를 사용했다.
          즉, lowCaloricDishes는 컨테이너 역할만 하는 중간 변수이다
          java8 에서는 이러한 세부 구현은 라이브러리 내에서 모두 처리한다.
         */

        //java 8
        List<String> lowCaloricDishesNameJava8 =
                menu.stream() // stream을 parallelStream()으로 바꾸면 이코드를 멀티코어 아키텍처에서 병렬로 실행할수 있다.
                        .filter(d -> d.getCalories() < 400) // 400칼로리 이하의 요리선택
                        .sorted(comparing(Dish::getCalories)) //칼로리로 요리정렬
                        .map(Dish::getName) //요리명 추출
                        .collect(toList()); //모든 요리명을 리스트에 저장
        System.out.println(lowCaloricDishesNameJava8);

        /**
         parallelStream()을 호출했을때 정확히 어떤 일이 일어나는지, 얼마나 많은 스레드가 사용되는지, 성능이 얼마나좋은지는 7장에서,
         */


    }
}
