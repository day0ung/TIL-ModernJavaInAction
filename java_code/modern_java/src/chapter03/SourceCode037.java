package chapter03;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

enum Color {RED, GREEN }

public class SourceCode037 {

    public static void main(String[] args)  {
        // 1
        List<Apple> inventory = new ArrayList<>();
        inventory.addAll(Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        ));
        /**
         void sort 코드는 Comparator 객체를 인수로 받아 두 사과를 비교한다.
         객체 안에 동작을 포함시키는 방식으로 다양한 전략을 전달할수 있다.
         이제 'sort의 동작은 파라미터화' 되었다
         */
        inventory.sort(new AppleComparator());
        System.out.println(inventory); // [Apple{color=GREEN, weight=80}, Apple{color=RED, weight=120}, Apple{color=GREEN, weight=155}]

        // reshuffling things a little
        inventory.set(1, new Apple(30, Color.GREEN));


        //2 익명 클래스 사용
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple a1, Apple a2) {
                return a1.getWeight() - a2.getWeight();
            }
        });
        System.out.println(inventory);  // [Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]

        // reshuffling things a little
        inventory.set(1, new Apple(20, Color.RED));


        // 3 람다 표현식 사용
        inventory.sort((a1, a2) -> a1.getWeight() - a2.getWeight());
        System.out.println(inventory); // [Apple{color=RED, weight=20}, Apple{color=GREEN, weight=30}, Apple{color=GREEN, weight=155}]

        // reshuffling things a little
        inventory.set(1, new Apple(10, Color.RED));

        // 4 메서드 참조 사용
        inventory.sort(comparing(Apple::getWeight));
        System.out.println(inventory); // [Apple{color=RED, weight=10}, Apple{color=RED, weight=20}, Apple{color=GREEN, weight=155}]


    }

    static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple a1, Apple a2) {
            return a1.getWeight() - a2.getWeight();
        }

    }



    static class Apple {
        private int weight = 0;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        @SuppressWarnings("boxing")
        @Override
        public String toString() {
            return String.format("Apple{color=%s, weight=%d}", color, weight);
        }

    }









}



