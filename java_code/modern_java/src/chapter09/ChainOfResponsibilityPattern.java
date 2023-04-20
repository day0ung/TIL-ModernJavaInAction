package chapter09;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainOfResponsibilityPattern {
    //작업처리 객체가 자신의 작업을 끝냈으면 다음 작업처리 객체로 결과를 전달
    public static void main(String[] args) {
        //기존 의무체인 패턴 코드
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2); // 두 작업 처리 객체를 연결한다
        String result1 = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result1);

        //람다 표현식 사용 (함수 체인(함수 조합)과 비슷하다)
        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text; //첫번째 작업처리 객체
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda"); // 두번째 작업 처리 객체
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing); //동작 체인으로 두함수를 조합
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);

        //** UnaryOperator  (Function의 서브 인터페이스)
        // 함수형 인터페이스 중 하나로, 입력값을 받아 연산을 수행한 후 동일한 타입의 값을 반환하는 함수입니다. 즉, 입력값과 출력값의 타입이 모두 같다.
        // Function은 입력값과 출력값의 타입이 서로 다를 수 있다.
    }

    private static abstract class ProcessingObject<T> {

        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        //일부 작업을 어떻게 처리 해야할지 전체적 기술
        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        //상속받아 메서드를 구현하여 다양한 종류의 작업 처리
        abstract protected T handleWork(T input);

    }

    private static class HeaderTextProcessing extends ProcessingObject<String> {

        @Override
        public String handleWork(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }

    }

    private static class SpellCheckerProcessing extends ProcessingObject<String> {

        @Override
        public String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        } //lambda에서 b를 바뜨림..

    }
}
