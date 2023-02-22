package chapter06;

import chapter04.Dish;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;

public class SourceCode064 {
    public static void main(String[] args) {
        List<Dish> menu = Dish.getMenu();

        //분할 (채식 요리와 채식이 아닌 요리를 분류)
        Map<Boolean, List<Dish>> partitionedMenu =
                menu.stream().collect(partitioningBy(Dish::isVegetarian));

        partitionedMenu.forEach( (b, dishes)  -> {
            dishes.forEach( d -> {System.out.print(d.getName() + " ");});
            System.out.println(":" + b);
        });
        // pork beef chicken prawns salmon :false
        //french fries rice season fruit pizza :true
    }
}
