package chapter10.dsl;

import chapter10.domain.*;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculator {

    public static double calculate(Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge) {
        double value = order.getValue();
        if (useRegional) {
            value = Tax.regional(value);
        }
        if (useGeneral) {
            value = Tax.general(value);
        }
        if (useSurcharge) {
            value = Tax.surcharge(value);
        }
        return value;
    }

    private boolean useRegional;
    private boolean useGeneral;
    private boolean useSurcharge;

    public TaxCalculator withTaxRegional() {
        useRegional = true;
        return this;
    }

    public TaxCalculator withTaxGeneral() {
        useGeneral= true;
        return this;
    }

    public TaxCalculator withTaxSurcharge() {
        useSurcharge = true;
        return this;
    }

    public double calculate(Order order) {
        return calculate(order, useRegional, useGeneral, useSurcharge);
    }

    public DoubleUnaryOperator taxFunction = d -> d; // 주문값에 적용된 모든 세금을 계산하는 함수

    public TaxCalculator with(DoubleUnaryOperator f) {
        taxFunction = taxFunction.andThen(f); // 새로운 세금 계산 함수를 얻어서 인수로 전달된 함수와 현재 함수를 합침
        return this; // 세금 함수가 연결될 수 있도록 결과반환
    }

    public double calculateF(Order order) {
        //주문의 총 합에 세금 계산 함수를 적용해 최종 주문값을 계산
        return taxFunction.applyAsDouble(order.getValue());
    }
}
