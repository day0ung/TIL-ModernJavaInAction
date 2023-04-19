package chapter09;

public class SourceCode091 {
    public static void main(String[] args) {
        //** 익명 클래스를 람다 표현식으로 리팩터링 **
        refactorClassToLambda();
        refactorClassToLambdaCompileError(); //익명클래스를 람다표현식으로 바꾸면 콘텍스트 오버로딩에 따른 모호함이 초래

        //익명 클래스는 인스턴스화 할때 명시적으로 형식이 정해지는 반면 람다의 형식은 콘텍스트에 따라 달라진다.
        doSomething(new Task() {
            @Override
            public void execute() {
                System.out.println("class");
            }
        });
        //doSomething(() -> System.out.println("lambda")); (Runnable, Task중 어느것을 가리키는지 모호함)
        doSomething((Task)() -> System.out.println("lambda"));

        //** 람다 표현식을 메서드 참조로 리팩터링 **

    }

    private static void refactorClassToLambda() {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };

        Runnable r2 = () -> System.out.println("Hello"); // 람다 표현식을 사용한 최신코드
    }


    private static void refactorClassToLambdaCompileError() {
        int a = 10;
        Runnable r1 = () -> {
            //int a = 2; //compile error
            System.out.println(a);
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 2; //정상 작동
                System.out.println(a);
            }
        };
    }



    interface Task{
        public void execute();
    }

    public static void doSomething(Runnable r){r.run();}
    public static void doSomething(Task a){ a.execute();}




}
