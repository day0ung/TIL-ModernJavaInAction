package chapter02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SourceCode022 {
    public static void main(String ... args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(180, Color.RED),
                 new Apple(120, Color.RED));

        //2.2.1
        List<Apple> redAndHeabyApple = filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeabyApple); //[Apple{color=RED, weight=180}]
    }



    //2.2.1 (2.1.3 filterApples메서드를 ApplePredicate 객체인수로 받도록 수정)
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    // 2.2 인터페이스 정의
    interface ApplePredicate {
        boolean test(Apple a);
    }
    //여러버전의 ApplePredicate를 정의
    static class AppleWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor() == Color.GREEN;
        }
    }
    //2.2.1
    static class AppleRedAndHeavyPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor() == Color.RED && apple.getWeight() > 150;
        }
    }
}
