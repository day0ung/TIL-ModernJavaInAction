package chapter17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TempSubscription implements Subscription {

  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  private final Subscriber<? super TempInfo> subscriber;
  private final String town;

  public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
    this.subscriber = subscriber;
    this.town = town;
  }

  @Override
  public void request(long n) {
    executor.submit(() -> { //다른 스레드에서 다음 요소를 구독자에게 보낸다.
      for (long i = 0L; i < n; i++) {
        try {
          subscriber.onNext(TempInfo.fetch(town));
        } catch (Exception e) {
          subscriber.onError(e);
          break;
        }
      }
    });
  }

  @Override
  public void cancel() {
    subscriber.onComplete();
  }

}
