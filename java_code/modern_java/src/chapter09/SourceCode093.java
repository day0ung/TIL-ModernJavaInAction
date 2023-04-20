package chapter09;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SourceCode093 {
    //1. 람다 테스팅
    @Test
    public void testMoveRightBy() throws Exception{
        Point p1 = new Point(5,5);
        Point p2 = p1.moveRightBy(10);
        assertEquals(15, p2.getX());
        assertEquals(5, p2.getY());
    }

    //2. 보이는 람다 표현식의 동작 테스팅
    @Test
    public void testComparingTwoPoints() throws Exception{
        Point p1 = new Point(10,15);
        Point p2 = new Point(10,20);
        int result = Point.compareByXAndThenY.compare(p1, p2);
        assertTrue(result < 0);
    }

    //3. 람다를 사용하는 메서드의 동작에 집중하라
    @Test
    public void testMoveAllPointsRightBy() throws Exception{
        List<Point> points = Arrays.asList(new Point(5,5), new Point(10,5));
        List<Point> expectedPoints = Arrays.asList(new Point(15,5), new Point(20,5));
        List<Point> newPoints = Point.moveAllPointsRightBy(points, 10);
        assertEquals(expectedPoints, newPoints);
    }


    static class Point{
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Point moveRightBy(int x){
            return new Point(this.x + x, this.y);
        }

        //2. 정적 필드 추가( 메서드 참조로 생성한 Comparator 객체에 접근할 수 있다.)
        //람다 표현식은 함수형 인터페이스의 인스턴스를 생성한다는 사실!! 기억
        public final static Comparator<Point> compareByXAndThenY
                = Comparator.comparing(Point::getX).thenComparing(Point::getY);

        //3. 사용하는 메서드의동작을 테스트함으로써 람다를 공개하지 않으면서도 검증할수 있다.
        public static List<Point> moveAllPointsRightBy(List<Point> points, int x){
            return points.stream()
                    .map(p -> new Point(p.getX() + x, p.getY())) //이부분의 테스트는 없다, 그냥 메서드를 구현한 코드이다.
                    .collect(toList());
        }
    }
}
