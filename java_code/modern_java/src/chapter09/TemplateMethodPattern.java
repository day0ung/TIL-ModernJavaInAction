package chapter09;

abstract class TemplateMethodPattern {
    // processCustomer 메서드는 온라인 뱅킹 알고리즘이 해야할일을 보여준다. 주어진 고객 ID를 이용해서 고객을 만족시켜야한다.
    // 각각의 지점은 TemplateMethodPattern 클래스를 상속받아 makeCustomerHappy 메서드가 원하는 동작을 수행하도록 구현할 수 있다.
    public void processCustomer(int id) {
        Customer c = TemplateMethodPattern.Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer c);

    // 더미 Customer 클래스
    static private class Customer {}

    // 더미 Database 클래스
    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }
}
