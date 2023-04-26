package chapter15;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

public class SourceCode155 {
    public static void main(String[] args) {
//        c1이나 c2가 값이 바뀌었을때 c3가 두 값을 더하도록 어떻게 지정할수 있을까?
//        SimpleCell.class
        System.out.println("==SimpleCell ==");
        SimpleCell c3 = new SimpleCell("C3");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c1 = new SimpleCell("C1");

        c1.subscribe(c3);

        c1.onNext(10); // C1의 값을 10으로 갱신
        c2.onNext(20); // C2의 값을 20으로 갱신

//        "C3=C1+C2"는 어떻게 구현할까?
//        ArithmeticCell.class
        System.out.println("==ArithmeticCell ==");
        test1();
        System.out.println("------------");
        test2();
    }

    private static void test1() {
        ArithmeticCell c3 = new ArithmeticCell("C3");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c1 = new SimpleCell("C1");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);

        c1.onNext(10); // C1의 값을 10으로 갱신
        c2.onNext(20); // C2의 값을 20으로 갱
        c1.onNext(15); // C1의 값을 15로 갱신
    }

    private static void test2() {
        ArithmeticCell c5 = new ArithmeticCell("C5");
        ArithmeticCell c3 = new ArithmeticCell("C3");
        SimpleCell c4 = new SimpleCell("C4");
        SimpleCell c2 = new SimpleCell("C2");
        SimpleCell c1 = new SimpleCell("C1");

        c1.subscribe(c3::setLeft);
        c2.subscribe(c3::setRight);

        c3.subscribe(c5::setLeft);
        c4.subscribe(c5::setRight);

        c1.onNext(10); // C1의 값을 10으로 갱신
        c2.onNext(20); // C2의 값을 20으로 갱신
        c1.onNext(15); // C1의 값을 15로 갱신
        c4.onNext(1); // C4의 값을 1로 갱신
        c4.onNext(3); // C4의 값을 3으로 갱신
    }


    //1. 값을 표함하는 셀 구현 SimpleCell
    /*
    c1이나 c2에 이벤트가발생했을때 c3을 구독하도록 만들어야한다. 그러려면  Publisher인터페이스가 필요하다
    -> interface Publisher<T> { void subscribe(Subscriber<? super Integer> subscriber)}
     */

    /*
    이 인터페이스는 통신할 구독자를 인수로 받는다. Subscriber 인터페이스는 onNext라는 정보를 전달할 단순 메서드를 포함하며
    구현자가 필요한대로 이 메서드를 구현할수 있다.
    -> interface Subscriber<T> { void onNext(T t);
     */

    //2. 위 두개념을 합치는 SimpleCell은 Publisher이며 동시에 Subscriber임을 알수 있다.
    static class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {

        private int value = 0;
        private String name;
        private List<Subscriber<? super Integer>> subscribers = new ArrayList<>();

        public SimpleCell(String name) {
            this.name = name;
        }

        @Override
        public void subscribe(Subscriber<? super Integer> subscriber) {
            subscribers.add(subscriber);
        }

        //새로운 값이 있음을 모둔 구독자에게 알리는 메서드
        private void notifyAllSubscribers() {
            subscribers.forEach(subscriber -> subscriber.onNext(value));
        }

        //구독한 셀에서 새 값이 생겼을때 값을 갱신해서 반응함
        @Override
        public void onNext(Integer newValue) {
            this.value = newValue;
            System.out.println(name + ":" + this.value); //값을 콘솔로 출력하지만 실제로는 UI의 셀을 갱신할수 있음
            notifyAllSubscribers(); //값이 갱신되었음을 모든 구독자에게 알림
        }

        public void subscribe(Consumer<? super Integer> onNext) {
            subscribers.add(new Subscriber<>() {

                @Override
                public void onComplete() {}

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                }

                @Override
                public void onNext(Integer val) {
                    onNext.accept(val);
                }

                @Override
                public void onSubscribe(Subscription s) {}

            });
        }

        @Override
        public void onComplete() {}

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onSubscribe(Subscription s) {}

    }


    // 왼쪽과 오른쪽의 연산 결과를 저장할수 있는 클래스가 필요하다
    static class ArithmeticCell extends SimpleCell {

        private int left;
        private int right;

        public ArithmeticCell(String name) {
            super(name);
        }

        public void setLeft(int left) {
            this.left = left;
            onNext(left + right);
        }

        public void setRight(int right) {
            this.right = right;
            onNext(right + left);
        }
    }
}
