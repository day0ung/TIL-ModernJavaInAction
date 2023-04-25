package chapter15;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;

public class SourceCode152 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //Thread Example
        threadExample();

        //ExecutorService Example
        executorServiceExample();

        //reactiveAPI
        //문제점: f,g의 호출합계를 정확하게 출력하지 않고 상황에 따라 먼저 계산된 결과를 출력한다.
        reactiveAPIExample();
    }

    private static void reactiveAPIExample() {
        int x = 1337;
        Result result = new Result();

        f(x, (int y) -> {
            result.left = y;
            System.out.println((result.left + result.right));
        });

        g(x, (int z) -> {
            result.right = z;
            System.out.println((result.left + result.right));
        });
    }


    private static void threadExample() throws InterruptedException {
        int x = 1337;
        Result result = new Result();

        Thread t1 = new Thread(() -> {
            result.left = f(x);
        });
        Thread t2 = new Thread(() -> {
            result.right = g(x);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(result.left + result.right);
    }

    private static void executorServiceExample() throws ExecutionException, InterruptedException {
        // Runnable 대신 Future API 인터페이스를 활용할 수 도 있다. 이미 ExecutorService로 스레드 풀을 설정했다는 가정하에 다음처럼 구현이 가능하다.
        int x = 1337;

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> y = executorService.submit(() -> fo(x));
        Future<Integer> z = executorService.submit(() -> go(x));
        System.out.println(y.get() + z.get());

        executorService.shutdown();
    }



    public static int f(int x) {
        return x * 2;
    }

    public static int g(int x) {
        return x + 1;
    }

    public static Integer fo(int x) {
        return Integer.valueOf(x * 2);
    }

    public static Integer go(int x) {
        return Integer.valueOf(x + 1);
    }

    //reactive(callback)에서 필요한 함수
    private static void f(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(f(x));
    }

    private static void g(int x, IntConsumer dealWithResult) {
        dealWithResult.accept(g(x));
    }

    private static class Result {
        private int left;
        private int right;
    }

}
