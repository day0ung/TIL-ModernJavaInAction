package chapter15;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SourceCode154 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //아래 두 코드는 f(x)의 실행이 끝나지 않거나 g(x)의 실행이 끝나지 않는 상황에서 get()을 기다려야 하므로 프로세싱 자원을 낭비할 수 있다.
        compelete1();
        compelete2();

        //CompletableFuture<T>에 thenCombine 메서드를 사용함으로 두 연산결과를 효과적으로 더할수 있다.
        comelete3();

    }

    private static void comelete3() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z)-> y + z); //이부분이 핵심!!
        executorService.submit(() -> a.complete(f(x)));
        executorService.submit(() -> b.complete(g(x)));

        System.out.println(c.get());
        executorService.shutdown();

        //Future a, b의 결과를 알지 못한 상태에서 thenCombine은 두 연산이 끝났을때 스레드 풀에서 실행된 연산을 만든다.
        // 결과를 추가하는 세번째 연산 c는 다른 두작업이 끝날때까지 스레드에서 실행되지 않는다.(먼저 시작해 블록되지 않음)

        //따라서 complete1, 2에서 발생했던 블록 문제가 어디에서도 일어나지 않는다.
        //Future의 연산이 두 번째로 종료 되는 상황에서 실제 필요한 스레드는 한개지만 스레드 풀의 두 스레드가 여전히 활성 상태다.
        //이전 complete1, 2에서 y+z연산은 f(x)또는 g(x)를 실행(블록가능성이 있는) 한 같은 스레드에서 수행했다.
        // 반면 thenCombine을 이용하면 f(x)와 g(x)가 끝난 다음에야 덧셈계산이 실행된다.
    }

    private static void compelete1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(f(x)));
        int b = g(x);
        System.out.println(a.get() + b);
        executorService.shutdown();
    }

    private static void compelete2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        executorService.submit(() -> a.complete(g(x)));
        int b = f(x);
        System.out.println(a.get() + b);
        executorService.shutdown();

    }



    public static int f(int x) {
        return x * 2;
    }

    public static int g(int x) {
        return x + 1;
    }

}
