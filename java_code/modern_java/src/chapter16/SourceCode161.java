package chapter16;

import java.util.concurrent.*;

public class SourceCode161 {
    public static void main(String[] args) {
        /*
        newCachedThreadPool()은 Java에서 제공하는 스레드 풀 중 하나로, 고정된 스레드 풀 크기가 없고, 요청에 따라 유동적으로 스레드를 생성하는 스레드 풀
         - 이 스레드 풀은 요청이 많은 경우에는 새로운 스레드를 계속 생성하고, 일정 시간 동안 요청이 없는 경우에는 생성된 스레드를 제거하여 자원을 효율적으로 관리
         - 따라서 일시적으로 많은 요청이 들어와도 스레드 풀이 적응하여 처리할 수 있으며, 많은 작업을 처리해야 하는 경우 유리

         ExecutorService 인터페이스는 Java에서 비동기식 실행을 위한 일반적인 인터페이스
          - submit() 메서드는 작업(쓰레드 실행)을 ExecutorService에 제출하고 해당 작업을 나타내는 Future 객체를 반환
          - submit() 메서드를 사용하여 스레드 실행 작업을 제출하고 제출된 작업을 수행하는 스레드 풀을 관리하는 ExecutorService 객체를 생성
            또한 해당 작업이 완료되는데 필요한 시간을 줄이고 성능을 개선하는 데 도움
         */
        ExecutorService executor = Executors.newCachedThreadPool();
                                            //callable을 ExecutorService로 제출한다.
                                            // 스레드 풀에 테스크를 제출하려면 ExecutorService을 만들어야한다.
        Future<Double> future = executor.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation(); //시간이 오래 걸리는 작업은 다른 스레드에서 비동기적으로 실행
            }
        });
        doSomethingElse(); //비동기 작업을 수행하는 동안 다른 작업을 한다.

        try {
            Double result = future.get(1000, TimeUnit.MILLISECONDS);
                                    //비동기 작업의 결과를 가져온다.
                                    //결과가 준비되어 있지 않으면 호출 스레드가 블록된다. 최대 1초까지만 기다린다
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); //현재 스레드에서 대기 중 인터럽트 발생
        } catch (ExecutionException e) {
            throw new RuntimeException(e); //계산중 예외발생
        } catch (TimeoutException e) {
            System.out.println("TimeoutException");
//            throw new RuntimeException(e); // Futurer가 완료 되기 전에 타임아웃 발생
        }

    }

    private static void doSomethingElse() {
        System.out.println("doSomethingElse");
    }

    private static Double doSomeLongComputation() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("doSomeLongComputation");
        return Double.valueOf(3.14);
    }
}
