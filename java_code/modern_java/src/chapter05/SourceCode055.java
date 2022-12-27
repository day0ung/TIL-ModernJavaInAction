package chapter05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SourceCode055 {

    public static void main(String[] args) {
        //5.5.1 요소의 합
        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        int sum = 0;
        for (int x : numbers){
            sum += x;
        }
        System.out.println(sum); //15

        int sum_reduce = numbers.stream().reduce(0, (a,b) -> a+b);
        System.out.println(sum_reduce); //15

        int sum_reduce_java8 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum_reduce_java8); //15

        //5.5.2 최댓값과 최솟값
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        Optional<Integer> max_other = numbers.stream().reduce((x,y) -> x>y? x:y);
        System.out.println(max); //Optional[5]
        System.out.println(max_other);  //Optional[5]
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        Optional<Integer> min_other = numbers.stream().reduce((x,y) -> x<y? x:y);
        System.out.println(min); //Optional[1]
        System.out.println(min_other); //Optional[1]

    }
}
