package chapter10;

import chapter10.domain.Tax;
import chapter10.dsl.LambdaOrderBuilder;
import chapter10.domain.Order;
import chapter10.domain.Stock;
import chapter10.domain.Trade;
import chapter10.dsl.TaxCalculator;

import static chapter10.dsl.MethodChainingOrderBuilder.*;
import static chapter10.dsl.NestedFunctionOrderBuilder.*;
import static chapter10.dsl.MixedBuilder.*;

public class SourceCode103_DSL {
    public static void main(String[] args) {
        SourceCode103_DSL dsl = new SourceCode103_DSL();
        //1. 기본 도메인 객체의 API를 직접 이용해 주식 거래 주문을 만든다.
        dsl.plain();

        //2. 메서드 체인으로 주식거래 주문 만들기, (한개의 메서드 호출 체인으로 거래 주문 정의할수 있다.)
        // 결과를 달성하려면 플루언트 API로 도메인 객체를 만드는 몇개의 빌더를 구현해야한다. MethodChainingOrderBuilder
        dsl.methodChaining();

        //3. 중첩된 함수 DSL을 제공하는 주문 빌더
        dsl.nestedFunction();

        //4. 람다 표현식을 이용한 함수 시퀀싱
        dsl.lambda();

        //5. DSL에 메서드 참조 사용하기 (주식 거래 도메인 모델에 다른 간단한 기능 추가)
        // 주문의 총합에 적용할 세금 Tax.class
        // 적용할 세금을 유창하게 정의하는 세금 계산기 TaxCalculator.class
        dsl.methodReference();

    }

    public void plain() {
        Order order = new Order();
        order.setCustomer("BigBank");

        Trade trade1 = new Trade();
        trade1.setType(Trade.Type.BUY);

        Stock stock1 = new Stock();
        stock1.setSymbol("IBM");
        stock1.setMarket("NYSE");

        trade1.setStock(stock1);
        trade1.setPrice(125.00);
        trade1.setQuantity(80);
        order.addTrade(trade1);

        Trade trade2 = new Trade();
        trade2.setType(Trade.Type.BUY);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGLE");
        stock2.setMarket("NASDAQ");

        trade2.setStock(stock2);
        trade2.setPrice(375.00);
        trade2.setQuantity(50);
        order.addTrade(trade2);

        System.out.println("Plain:");
        System.out.println(order);
    }

    public void methodChaining() {
        Order order = forCustomer("BigBank")
                .buy(80).stock("IBM").on("NYSE").at(125.00)
                .sell(50).stock("GOOGLE").on("NASDAQ").at(375.00)
                .end();

        System.out.println("Method chaining:");
        System.out.println(order);
    }

    public void nestedFunction() {
        Order order = order("BigBank",
                buy(80,
                        stock("IBM", on("NYSE")),
                        at(125.00)),
                sell(50,
                        stock("GOOGLE", on("NASDAQ")),
                        at(375.00))
        );

        System.out.println("Nested function:");
        System.out.println(order);
    }

    public void lambda() {
        Order order = LambdaOrderBuilder.order(o -> {
            o.forCustomer( "BigBank" );
            o.buy( t -> {
                t.quantity(80);
                t.price(125.00);
                t.stock(s -> {
                    s.symbol("IBM");
                    s.market("NYSE");
                });
            });
            o.sell( t -> {
                t.quantity(50);
                t.price(375.00);
                t.stock(s -> {
                    s.symbol("GOOGLE");
                    s.market("NASDAQ");
                });
            });
        });

        System.out.println("Lambda:");
        System.out.println(order);
    }

    public void mixed() {
        Order order =
                forCustomer("BigBank",
                        buy(t -> t.quantity(80)
                                .stock("IBM")
                                .on("NYSE")
                                .at(125.00)),
                        sell(t -> t.quantity(50)
                                .stock("GOOGLE")
                                .on("NASDAQ")
                                .at(375.00)));

        System.out.println("Mixed:");
        System.out.println(order);
    }

    public void methodReference(){
        Order order =
                forCustomer("BigBank",
                        buy(t -> t.quantity(80)
                                .stock("IBM")
                                .on("NYSE")
                                .at(125.00)),
                        sell(t -> t.quantity(50)
                                .stock("GOOGLE")
                                .on("NASDAQ")
                                .at(125.00)));

        double value = new TaxCalculator().calculate(order, true, false, true);
        System.out.printf("Boolean arguments: %.2f%n", value);

        value = new TaxCalculator().withTaxRegional()
            .withTaxSurcharge()
            .calculate(order);
        System.out.printf("Method chaining: %.2f%n", value);

        value = new TaxCalculator().with(Tax::regional)
            .with(Tax::surcharge)
            .calculateF(order);
        System.out.printf("Method references: %.2f%n", value);
    }

}
