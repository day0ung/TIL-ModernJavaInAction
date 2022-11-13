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

        //퀴즈 (2-1) 유연한 prettyPrintApple 메서드 구하기
        /*
        사과 리스트를 인수로 받아 다양한 방법으로 문자열을 생성(커스터마이즈된 다양한 toString메서드와 같이)할수 있도록 파라미터화된
        prettryPrintApple 메서드를 구현
         */
        prettryPrintApple(inventory, new AppleSimpleFormatter());
        //An apple of 80g
        //An apple of 155g
        //An apple of 180g
        //An apple of 120g
        prettryPrintApple(inventory, new AppleFancyFormatter());
        //A light GREEN apple
        //A heavt GREEN apple
        //A heavt RED apple
        //A light RED apple
        //
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

    //퀴즈 2-1
    public interface AppleFormatter{ String accept(Apple apple); };

    static class AppleFancyFormatter implements AppleFormatter{
        @Override
        public String accept(Apple apple) {
            String characteristic = apple.getWeight() > 150 ?  "heavt" : "light";

            return "A "+ characteristic + " " + apple.getColor() + " apple";
        }
    }

    static class AppleSimpleFormatter implements AppleFormatter{
        @Override
        public String accept(Apple apple) {
            return "An apple of " + apple.getWeight() + "g";
        }
    }
    //method
    public static void prettryPrintApple(List<Apple> inventory, AppleFormatter formatter){
        for (Apple apple : inventory) {
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }

}
