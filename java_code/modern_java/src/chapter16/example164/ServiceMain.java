package chapter16.example164;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

public class ServiceMain {

    public static void main(String[] args) {
        Shop shop = new Shop("shop");
        //getPrice의 메서드 변경 Basic 16
        System.out.println(shop.getPrice("phone"));

        //Discount서비스를 이용하는 간단한 findPrice구현
        //16.4.2 할인서비스 사용
        execute("Basic , " ,  () -> PriceFinder.findPrices("myPhone"));

        //16.4.3 동기 작업과 비동기 작업 조합
        execute("CompletableFuture , " ,  () -> PriceFinder.findPricesWithCompletableFuture("myPhone"));

        //16.4.4 독립 CompletableFuture와 비독립 CompletableFuture 합치기
        execute("findPricesInUSD , " ,  () -> PriceFinder.findPricesInUSD("myPhone"));

        //16.4.5 Future의 리플렉션과 CompletableFuture의 리플렉션
        execute("findPricesInUSDJava7 , " ,  () -> PriceFinder.findPricesInUSDJava7("myPhone"));
        execute("findPricesInUSD2 , " ,  () -> PriceFinder.findPricesInUSD2("myPhone"));
        execute("findPricesInUSD3 , " ,  () -> PriceFinder.findPricesInUSD3("myPhone"));



    }


    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }

}
