package chapter10.dsl;

import chapter10.domain.Order;
import chapter10.domain.Stock;
import chapter10.domain.Trade;

    // 최상위 수준 빌더를 만들고 주문을 감싼 다음 한개 이상의 거래를 주문에 추가할수 있어야한다.
public class MethodChainingOrderBuilder {

    public final Order order = new Order(); // 빌더로 감싼 주문

    private MethodChainingOrderBuilder(String customer) {
        order.setCustomer(customer);
    }

    //고객의 주문을 만드는 정적 팩토리 메서드
    public static MethodChainingOrderBuilder forCustomer(String customer) {
        return new MethodChainingOrderBuilder(customer);
    }

    public Order end() {
        return order;
    }

    //주식을 사는 TradeBuilder 생성
    public TradeBuilder buy(int quantity) {
        return new TradeBuilder(this, Trade.Type.BUY, quantity);
    }

    //주식을 파는 TradeBuilder 생성
    public TradeBuilder sell(int quantity) {
        return new TradeBuilder(this, Trade.Type.SELL, quantity);
    }

    private MethodChainingOrderBuilder addTrade(Trade trade) {
        order.addTrade(trade); // 주문에 주식 추가
        return this; //유연하게 추가 주문을 만들어 추가할 수 있도록 주문 빌더 자체를 반환
    }

    public static class TradeBuilder {

        private final MethodChainingOrderBuilder builder;
        public final Trade trade = new Trade();

        // 빌더를 계속 이어가려면 Stock클래스의 인스턴스를 만드는 TradeBuilder의 공개 메서드를 이용해야한다.
        private TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
            this.builder = builder;
            trade.setType(type);
            trade.setQuantity(quantity);
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(builder, trade, symbol);
        }

    }

    public static class StockBuilder {

        private final MethodChainingOrderBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(MethodChainingOrderBuilder builder, Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        // StockBuilder는 주식의 시장을 지정하고, 거래에 주식을 추가하고, 최정 빌더를 반환하는 on()메서드 하나를 정의
        public TradeBuilderWithStock on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return new TradeBuilderWithStock(builder, trade);
        }

    }

    public static class TradeBuilderWithStock {

        private final MethodChainingOrderBuilder builder;
        private final Trade trade;

        //거래되는 주식의 단위 가격을 설정한 다음, 원래 주문 빌더를 반환한다.
        public TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade) {
            this.builder = builder;
            this.trade = trade;
        }

        public MethodChainingOrderBuilder at(double price) {
            trade.setPrice(price);
            return builder.addTrade(trade);
        }

    }

}
