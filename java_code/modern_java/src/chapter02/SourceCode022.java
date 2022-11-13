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

        //2.3.2 익명클래스 사용
        List<Apple> redApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple a) {
                return Color.RED.equals(a.getColor());
            }
        });
        System.out.println("redApples:" +redApples); //redApples:[Apple{color=RED, weight=180}, Apple{color=RED, weight=120}]

        //2.3.3 람다표현식 사용
        List<Apple> result = filterApples(inventory, (Apple apple) -> Color.RED.equals(apple.getColor()));
        System.out.println("result lambda:" +redApples); //result lambda:[Apple{color=RED, weight=180}, Apple{color=RED, weight=120}]


        //퀴즈 2-2 익명클래스 문제 예시
         class MeaningOfThis{
         public final int value = 4;
         public void doit(){
             int value = 6;
             Runnable r = new Runnable() {
                 @Override
                 public void run() {
                     int value = 10;
                     System.out.print("this.value"); //eror발생으로 문자열처리
                 }
             };
             r.run();
           }
         }
         /*
         MeaningOfThis m = new MeaningOfThis();
         m.doIt(); <-  이행의 출력결과는?

         ::정답  this는 MeaningOfThis가 아니라 Runnable을 참조하므로 5가 정답!
          */

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
