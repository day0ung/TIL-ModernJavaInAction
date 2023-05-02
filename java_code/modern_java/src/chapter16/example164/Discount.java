package chapter16.example164;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class Discount {
  private Random random;
  private static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
  //enum으로 할인코드 정의
  public enum Code {
    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

    private final int percentage;

    Code(int percentage) {
      this.percentage = percentage;
    }
  }

  public static String applyDiscount(Quote quote) {
    return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
  }

  private static double apply(double price, Code code) {
    delay(); //discount 서비스의 응답지연을 흉내낸다.
    return format(price * (100 - code.percentage) / 100);
  }

  public static double format(double number) {
    synchronized (formatter) {
      return new Double(formatter.format(number));
    }
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
