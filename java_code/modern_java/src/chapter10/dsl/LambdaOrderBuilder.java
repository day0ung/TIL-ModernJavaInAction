package chapter10.dsl;

import chapter10.domain.Order;
import chapter10.domain.Stock;
import chapter10.domain.Trade;

import java.util.function.Consumer;

public class LambdaOrderBuilder {

    private Order order = new Order(); // 빌더로 주문을 감쌈

    public static Order order(Consumer<LambdaOrderBuilder> consumer) {
        LambdaOrderBuilder builder = new LambdaOrderBuilder();
        consumer.accept(builder); // 주문빌더로 전달된 람다 표현식 실행
        return builder.order; // OrderBuilder의 Consumer를 실행히 만들어진 주문을 반환
    }

    //주문을 요청한 고객 설정
    public void forCustomer(String customer) {
        order.setCustomer(customer);
    }

    //주식 매수 주문을 만들 도록 TradeBuilder 소비
    public void buy(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.BUY);
    }

    //주식 매도 주문을 만들 도록 TradeBuilder 소비
    public void sell(Consumer<TradeBuilder> consumer) {
        trade(consumer, Trade.Type.SELL);
    }

    private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
        TradeBuilder builder = new TradeBuilder();
        builder.trade.setType(type);
        consumer.accept(builder); //TradeBuilder로 전다할 람다 표현식 실행
        order.addTrade(builder.trade); // TradeBuilder의 Consumer를 실행해 만든 거래를 주문에 추가
    }

    public static class TradeBuilder {

        private Trade trade = new Trade();

        public void quantity(int quantity) {
            trade.setQuantity(quantity);
        }

        public void price(double price) {
            trade.setPrice(price);
        }

        public void stock(Consumer<StockBuilder> consumer) {
            StockBuilder builder = new StockBuilder();
            consumer.accept(builder);
            trade.setStock(builder.stock);
        }

    }

    public static class StockBuilder {

        private Stock stock = new Stock();

        public void symbol(String symbol) {
            stock.setSymbol(symbol);
        }

        public void market(String market) {
            stock.setMarket(market);
        }


    }
}
