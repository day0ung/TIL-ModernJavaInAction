package chapter16.example162;

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

    public String getName() {
        return name;
    }



    // 제품명에 해당하는 가격을 반환
    public double getPrice(String product){
        return calculatePrice(product);
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

    //16.2.2 에러처리방법
    public Future<Double> getPriceAsyncDealError(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price); //계산이 정상적으로 종료되면 Future에 가격 정보를 저장한채로 Future를 종료
            } catch (Exception e) {
                futurePrice.completeExceptionally(e); //도중에 문제가 발생하면 발생한 에러를 포함시켜 Future를 종료
            }
        }).start();
        return futurePrice; //계산결과가 완료되길 기다리지 않고 반환
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



}
