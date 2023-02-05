# 스트림으로 데이터 수집
 * [Collector](#61-collector)
 * [리듀싱과 요약](#62-리듀싱과-요약)
 * [그룹화](#63-그룹화)
 * [분할](#64-분할)
 * [Collector 인터페이스](#65-collector-인터페이스)
 * [커스텀 컬렉터를 구현해서 성능 개선하기](#66-커스텀-컬렉터를-구현해서-성능-개선하기)


## 6.1 Collector

Collector 인터페이스 구현은 스트림 요소를 어떤 식으로 도출할지 지정한다. 훌륭하게 설계된 함수형 API의 장점으로는 높은 수준의 조합성과 재사용성을 꼽을 수 있다. Collector 인터페이스 메서드를 어떻게 구현하느냐에 따라 스트림에 어떤 리듀싱 연산을 수행할지 결정된다. Collectors 유틸리티 클래스는 자주 사용하는 컬렉터 인스턴스를 손쉽게 생성할 수 있는 정적 팩토리 메서드를 제공한다.

Collectors에서 제공하는 메서드의 기능은 크게 세 가지로 구분할 수 있다.

* 스트림 요소를 하나의 값으로 리듀스하고 요약
* 요소 그룹화
* 요소 분할

## 6.2 리듀싱과 요약
컬렉터 (Stream.collect 메서드의 인수)로 스트림의 항목을 컬렉션으로 재구성 할수 있다. 즉, 스트림의 모든 항목을 하나의 결과로 합칠 수 있다.   
* 예제: counting() 팩터리 메서드가 반환하는 컬렉터로 수 계산
    * ~~~java
       long hoManyDishes = menu.stream().collect(Collectors.coungting());

       //Collectors 과정생략
       long hoManyDishes =  menu.stream().count();
       ~~~

**스트림 값에서 최댓값과 최솟값 검색**  
Collectors.maxBy , Collectors.minBy 
* 예제: Comparator로 칼로리를 비교 후 Collector.maxBy로 전달하는 코드 
    * ~~~java
       Comparator<Dish> compare = Comparator.comparingInt(Dish::getCalories);

       Optional<Dish> max = menu.stream().collect(maxBy(compare))
       ~~~
  
**요약연산**  
Collectors.summingInt  
객체를 int로 매핑하는 함수를 인수로 받는다. 
~~~java
int total = menu.stream().collect(summingInt(Dish::getCalories));
~~~
이러한 연산외에 평균간 계산등의 연산도 요약기능이 제공된다.  
* averagingInt, averagingLong, averagingDouble


**문자열 연결**  
joining  
스트림의 각 객체에 toString메서드를 호출해서 추출한 모든 문자열을 하나의 문자열로 연결해서 반환한다.  
~~~java
String menu = menu.stream().map(Dish::getName).collect(joining());
~~~
**범용 리듀싱 요약연산**  
지금까지 살펴본 모든 컬렉터는 reducing 팩터리 메서드로도 정의할수 있다.(즉, Collecttors.reduing)

~~~java
//모든 칼로리 합계를 계산 (인수 3개)
int total = menu.stream().collect(reduing(0,Dish::getCalories, (i,j -> i+j )))
~~~
* reducing은 인수 세개를 받는다. 
    * 첫번째 인수: 리듀싱연산의 시작값 혹은 인수가 없을때 반환값
    * 두번째 인수: 요리를 칼로리 정수로 변환, 변환함수
    * 세번째 인수: 같은종류의 두항목을 하나의 값으로 더함

~~~java
//모든 칼로리 합계를 계산(인수 1개)
Optional<Dish> total = menu.stream().collect(reduing(
    (d1,d2) -> d1.getCalories() > d2.getCalories() ? d1 :d2
    ));
~~~

>💡collect와 reduce  
collect와 reduce를 이용하면 동일한 기능을 구현할 수 있다. 하지만 의미론적인 문제와 실용성 문제등에 대하여 차이가 존재한다.  
collect 메서드는 도출하려는 결과를 누적하는 컨테이너를 바꾸도록 설계된 메서드인 반면, reduce는 두 값을 하나로 도출하는 불변형 연산이라는 점에서 의미론적인 차이가 존재한다.  
여러 스레드가 동시에 같은 데이터 구조체를 고치면 리스트 자체가 망가져버리므로 리듀싱 연산을 병렬로 수행할 수 없다. 이럴 때 가변 컨테이너 관련 작업이면서 병렬성을 확보하려면 collect 메서드로 리듀싱 연산을 구현하는것이 바람직하다.



**✍ 리듀싱과 요약 (컬렉터 인스턴스 활용)** 
* counting - 개수를 카운트한다
* maxBy, minBy - 최대 혹은 최소를 만족하는 요소를 찾는다
* summingInt - 객체를 int로 매핑하는 인수를 받아 합을 계산한다
* averagingInt - 객체를 int로 매핑하는 인스를 받아 평균을 계산한다
* summarizingInt - 요소 수, 합계, 평균, 최댓값, 최솟값 등을 계산한다.
* joining - 내부적으로 StringBuilder를 이용해서 문자열을 하나로 만든다.

## 6.3 그룹화

## 6.4 분할
## 6.5 Collector 인터페이스
## 6.6 커스텀 컬렉터를 구현해서 성능 개선하기