package chapter01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/*
Apple 클래스와 getColor메서드가 있고, Apples 리스트를 포함하는 변수  inventory가 있다고 가정
이때, 모든 녹색사과를 선택해서 리스트를 반환하려는 프로그램 구현
 */

public class SourceCode013 {
    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(120, "red")
        );

        //처음예제
        List<Apple> greenApples = filterGreenApples(inventory);
        List<Apple> heavyApples = filterHeavyApples(inventory);
        System.out.println(greenApples); // [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        System.out.println(heavyApples); // [Apple{color='green', weight=155}]

        //자바8사용
        List<Apple> greenApples2 = filterApples(inventory, Apple::isGreenApple);
        System.out.println(greenApples2); //[Apple{color='green', weight=80}, Apple{color='green', weight=155}]

        List<Apple> heavyApples2 = filterApples(inventory, Apple::isHeavyApple);
        System.out.println(heavyApples2); // [Apple{color='green', weight=155}]

        //메서드 전달에서 람다로
        //( 자바 8사용처럼, Apple 클래스 내부에 isGreenApple, isHeavyApple이 정의되어있어야한다. 매번정의하는것은 귀찮으니 람다 사용)
        List<Apple> greenApplesLambda = filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));
        System.out.println("lambda: "+greenApplesLambda); //lambda: [Apple{color='green', weight=80}, Apple{color='green', weight=155}]
        List<Apple> heavyApplesLambda = filterApples(inventory, (Apple a) -> a.getWeight() > 150);
        System.out.println("lambda: "+heavyApplesLambda); //lambda: [Apple{color='green', weight=155}]
        List<Apple> other = filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "red".equals(a.getColor()));
        System.out.println("lambda: "+other); //lambda: [Apple{color='red', weight=120}]

    }

    /*======================================================*/
    // 1. 녹색사과를 선택해서 리스트 반환
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) { //녹색사과만 선택
                result.add(apple);
            }
        }
        return result;
    }

    // 2. 사과를 무게 150이상을 필터링
    public static List<Apple> filterHeavyApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() >  150) { // 150이상만 선택
                result.add(apple);
            }
        }
        return result;
    }
    /*
    우리는 2번 코드를 추가하기위해 1을 복붙하여 사용할 것이다. 소프트웨어 공학적인 면에서
    복붙의 단점은 '어떤코드에 버그가 있으면 복붙한 모든 코드를 고쳐야한다.'
    예제에서 //주석처리한 부분의 한줄의 코드만 다르다
     */
    /*======================================================*/


    /*======================================================*/
    //아래두 함수는 예제(filterApples)사용시 error발생 Apple클래스 내부에 정의되어있어야함
    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }
    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }


    public interface Predicate<T> {
        boolean test(T t);
    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    /*
    앞의 코드를 자바 8에 맞게 구현
    Predicate<Apple> p -> 메서드가 p라는 이름의 프레디케이트 파라미터로 전달됨
     */

    /*======================================================*/



    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @SuppressWarnings("boxing")
        @Override
        public String toString() {
            return String.format("Apple{color='%s', weight=%d}", color, weight);
        }

        public static boolean isGreenApple(Apple apple) {
            return "green".equals(apple.getColor());
        }
        public static boolean isHeavyApple(Apple apple) {
            return apple.getWeight() > 150;
        }
    }


}
