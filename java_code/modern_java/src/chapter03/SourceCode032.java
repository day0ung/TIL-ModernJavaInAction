package chapter03;

public class SourceCode032 {

    //3.2.1 함수형 인터페이스
    public static void main(String[] args){
        /*람다사용*/
        Runnable r1 = () -> System.out.println("Hello World 1");
        process(r1); // Hello World 1

        /*익명클래스 사용*/
        Runnable r2= new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World 2");
            }
        };
        /*직접 전달된 람다 표현식*/
        process(r2);
        process(() ->System.out.println("Hello World 3"));// Hello World 3

    }

    public static void process(Runnable r){
        r.run();
    }
}
