package chapter13;

public class SourceCode134 {

    public static void main(String... args) {
        new C().hello(); // 무엇이 출력될까? : Hello from B
    }

    static interface A {
        public default void hello() {
            System.out.println("Hello from A");
        }

    }

    static interface B extends A{

        public default void hello() {
            System.out.println("Hello from B");
        }

    }

    static class C implements B, A {

    }
}
