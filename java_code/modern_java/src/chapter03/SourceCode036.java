package chapter03;


import java.util.function.Function;
import java.util.function.Supplier;

public class SourceCode036 {
    public static void main(String[] args)  {

        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get(); // Supplier의 get메서드를 호출해서 새로운 Apple 객체를 만들수 있다.

        //Apple(Integer weight)라는 시그니처를 갖는 생성자는 Function 인터페이스의 시그니처와 같다
        Function<Integer, Apple> c2 = Apple::new;
        Apple a2 = c2.apply(110);
        System.out.println(a2.getWeight()); //110


    }

    static class Apple {
        private Integer weight = 0;
        public Apple() {}

        public Apple(Integer weight) {
            this.weight = weight;
        }

        public Integer getWeight() {
            return weight;
        }
    }
}
