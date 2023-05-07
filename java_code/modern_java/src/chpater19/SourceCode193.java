package chpater19;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class SourceCode193 {
    public static void main(String[] args) {
        MyList<Integer> l = new MyLinkedList<>(5, new MyLinkedList<>(10, new Empty<>()));

        System.out.println(l.head());

        LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();
        System.out.println(two + " " + three + " " + four);

        numbers = from(2);
        int prime_two = primes(numbers).head();
        int prime_three = primes(numbers).tail().head();
        int prime_five = primes(numbers).tail().tail().head();
        System.out.println(prime_two + " " + prime_three + " " + prime_five);

        // 자바는 꼬리 호출 제거 기능이 없으므로 스택오버플로가 발생할 때까지 실행됨
        // printAll(primes(from(2)));
    }

    //======기본적인 연결리스트 START =====
    interface MyList<T> {

        T head();

        MyList<T> tail();

        default boolean isEmpty() {
            return true;
        }

        MyList<T> filter(Predicate<T> p);

    }

    static class MyLinkedList<T> implements MyList<T> {

        final T head;
        final MyList<T> tail;

        public MyLinkedList(T head, MyList<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public MyList<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public MyList<T> filter(Predicate<T> p) {
            return isEmpty() ? this : p.test(head()) ? new MyLinkedList<>(head(), tail().filter(p)) : tail().filter(p);
        }

    }
    //======기본적인 연결리스트 END=====

    static class Empty<T> implements MyList<T> {

        @Override
        public T head() {
            throw new UnsupportedOperationException();
        }

        @Override
        public MyList<T> tail() {
            throw new UnsupportedOperationException();
        }

        @Override
        public MyList<T> filter(Predicate<T> p) {
            return this;
        }

    }

    //======기본적인 게으른 리스트 START=====
    static class LazyList<T> implements MyList<T> {

        final T head;
        final Supplier<MyList<T>> tail;

        public LazyList(T head, Supplier<MyList<T>> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public MyList<T> tail() {
            return tail.get(); //위의 head와 달리 tail에서는 Supplier로 게으른 동작을 만들었다.
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override //게으른 필터구현
        public MyList<T> filter(Predicate<T> p) {
            return isEmpty() ? this : p.test(head()) ? new LazyList<>(head(), () -> tail().filter(p)) : tail().filter(p);
        }

    }
    //======기본적인 게으른 리스트 END=====


    //Supplier의 get메서드를 호출하면 마치 새로운 객체를 생성하듯이 lazylist의 노드가 만들어진다.
    //이제 연속적인 숫자의 다음 요소를 만드는 LazyList의 생성자에 tail인수로 Supplier를 전달하는 방식으로 n으로 시작하는 무한히 게으른 리스트를만들수있다.
    public static LazyList<Integer> from(int n) {
        return new LazyList<Integer>(n, () -> from(n + 1));
    }

    //소수 생성으로 돌아와서
    public static MyList<Integer> primes(MyList<Integer> numbers) {
        return new LazyList<>(numbers.head(), () -> primes(numbers.tail().filter(n -> n % numbers.head() != 0)));
    }

    //재귀적으로 호출
    static <T> void printAll(MyList<T> numbers) {
        if (numbers.isEmpty()) {
            return;
        }
        System.out.println(numbers.head());
        printAll(numbers.tail());
    }
}
