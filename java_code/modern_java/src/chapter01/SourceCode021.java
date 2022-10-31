package chapter01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum Color {RED, GREEN }

public class SourceCode021 {

    public static void main(String ... args){
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED));

        //2.1.1
        List<Apple> greenApples = filterGreenApples(inventory);
        System.out.println(greenApples); //[Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]

        //2.1.2
        List<Apple> redApples = filterApplesByColor(inventory, Color.RED);
        System.out.println(redApples); // [Apple{color=RED, weight=120}]
    }


    //2.1.1 녹색사과 필터링
    public static List<Apple> filterGreenApples(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor() == Color.GREEN) {
                result.add(apple);
            }
        }
        return result;
    }

    //2.1.2 색을 파라미터화
    public static List<Apple> filterApplesByColor(List<Apple> inventory, Color color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getColor().equals(color)) {
                result.add(apple);
            }
        }
        return result;
    }


    //apple 클래스 정의
    public static class Apple {

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



