package chapter16.example163;

import java.util.List;
import java.util.function.Supplier;

public class PriceFinderMain {
    public static void main(String[] args) {
        //findPrices Basic
        long start = System.nanoTime();
        System.out.println(PriceFinder.findPrices("myPhone"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Basic Done in "+duration+"msecs");

        //findPrices parallel
        long start_parallel = System.nanoTime();
        System.out.println(PriceFinder.findPricesWithParallel("myPhone"));
        long duration_parallel = (System.nanoTime() - start_parallel) / 1_000_000;
        System.out.println("Parallel Done in "+duration_parallel+ "msecs");

        //findPrice CompletableFuture -> 아주 조금빠르다
        long start_completableFuture = System.nanoTime();
        System.out.println(PriceFinder.findPricesWithCompletableFuture("myPhone"));
        long duration_completableFuture = (System.nanoTime() - start_completableFuture) / 1_000_000;
        System.out.println("CompletableFuture Done in "+duration_completableFuture+ "msecs");

        //findPricesWithCustomExecutor
        long start_completableFuture_custom = System.nanoTime();
        System.out.println(PriceFinder.findPricesWithCustomExecutor("myPhone"));
        long duration_completableFuture_custom = (System.nanoTime() - start_completableFuture_custom) / 1_000_000;
        System.out.println("CompletableFuture With Executor Done in "+duration_completableFuture_custom+ "msecs");



    }


}
