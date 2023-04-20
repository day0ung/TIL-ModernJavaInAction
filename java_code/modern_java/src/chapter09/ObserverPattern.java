package chapter09;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
    //다양한 신문매체가 뉴스 트위을 구독하고 있으며 특정 키워드를 포함하는 트윗이 등록되면 알림을 받도록 설계

    public static void main(String[] args) {
//        Feed f = new Feed();
//        f.registerObserver(new NYTimes());
//        f.registerObserver(new Guardian());
//        f.registerObserver(new LeMonde());
//        f.notifyObservers("The queen said her favourite book is Java 8 & 9 in Action!");

        Feed feedLambda = new Feed();

        feedLambda.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        });
        feedLambda.registerObserver((String tweet) -> {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        });

        feedLambda.notifyObservers("Money money money, give me money!");
    }

    //1. 다양한 옵저버를 그룹화할 인터페이스 정의
    interface Observer {
        void notify(String tweet); // 새로운 트윗이 있을때 주제(Feed)가 호출 될수 있도록 메서드 정의
    }


    //2. 트윗에 포함된 다양한 키워드에 다른 동작을 수행할수 있는 여러 옵저버 정의
    static private class NYTimes implements Observer {

        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("NYTimes : " + tweet);
            }
        }

    }

    static private class Guardian implements Observer {

        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Guardian : " + tweet);
            }
        }

    }

    static private class LeMonde implements Observer {

        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("LeMonde : " + tweet);
            }
        }

    }


    //3. 주제 구현,
    interface Subject {
        void registerObserver(Observer o);
        void notifyObservers(String tweet);
    }

    //4.  Subject는 registerObserver메서드로 새로운 옵저버를 등록한 후에 notifyObservers 메서드로 트윗의 옵저버에 이를 알린다.
    static private class Feed implements Subject {

        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            observers.add(o);
        }

        @Override
        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }
}
