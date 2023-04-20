package chapter09;

import java.util.function.Consumer;

public class TemplateMethodPatternLambda {

    public static void main(String[] args) {
        new TemplateMethodPatternLambda().processCustomer(1337, (Customer c) -> System.out.println("Hello!"));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    // 더미 Customer 클래스
    static private class Customer {}

    // 더미 Database 클래스
    static private class Database {

        static Customer getCustomerWithId(int id) {
            return new Customer();
        }

    }
}
