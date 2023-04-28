package chapter16.application;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

//16.2 비동기 API구현
public class Shop {
    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }


    // 제품명에 해당하는 가격을 반환
    public double getPrice(String product){
        return calculatePrice(product);
    }

    //임의의 계산값을 반환하도록
    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }


    // 1초 지연을 흉내내는 메서드 (실제 호출할 서비스까지 구현하지않으므로)
    public static void delay(){
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //16.2.1 동기 메서드를 비동기 메서드로 변환
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>(); //계산결과를 포함할 CompletableFuture생성
        new Thread(() -> {
            double price = calculatePrice(product);  //다른 스레드에서 비동기적으로 계산수행
            futurePrice.complete(price); // 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정
        }).start();
        return futurePrice; //계산결과가 완료되길 기다리지 않고 반환
    }


}
