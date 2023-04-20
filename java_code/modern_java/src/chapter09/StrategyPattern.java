package chapter09;

public class StrategyPattern {
    // 소문자 또는 숫자로 이루어져야하는 등 텍스트입력이 다양한 조건에 맞게 포맷되어있는지 검증
    public static void main(String[] args) {
        // old school
        Validator v1 = new Validator(new IsNumeric());
        System.out.println(v1.validate("aaaa"));
        Validator v2 = new Validator(new IsAllLowerCase());
        System.out.println(v2.validate("bbbb"));

        // with lambdas
        Validator v3 = new Validator((String s) -> s.matches("\\d+"));
        System.out.println(v3.validate("aaaa")); //false반환
        Validator v4 = new Validator((String s) -> s.matches("[a-z]+"));
        System.out.println(v4.validate("bbbb")); //true반환
    }

    //1. String 문자열을 검증하는 인터페이스 구현
    interface ValidationStrategy {
        boolean execute(String s);
    }


    //2. 인터페이스를 구현하는 클래스 하나이상 정의
    static private class IsAllLowerCase implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }

    }

    static private class IsNumeric implements ValidationStrategy {
        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }

    }
    //=======================================

    //3. 구현한 클래스를 다양한 검증전략으로 활용
    static private class Validator {

        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy v) {
            strategy = v;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }

    }
}
