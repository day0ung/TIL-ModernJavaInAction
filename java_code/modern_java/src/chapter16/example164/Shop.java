package chapter16.example164;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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



    // 제품명에 해당하는 가격을 반환 기존 16.2 버전에서 변경
    // 미리 계산된 임의의 가격과 임의의 DisCount.Code 반환
    public String getPrice(String product){
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public double getPriceDouble(String product){
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



}
