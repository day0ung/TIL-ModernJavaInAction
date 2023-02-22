# 스트림 활용
 스트림 API가 지원하는 다양한 연산들
 * [필터링](#51-필터링)
 * [스트림 슬라이싱](#52-스트림-슬라이싱)
 * [매핑](#53-매핑)
 * [검색과 매칭](#54-검색과-매칭)
 * [리듀싱](#55-리듀싱)
 * [숫자형 스트림](#56-숫자형-스트림)
 * [스트림 만들기](#57-스트림-만들기)

</br>
</br>

## 5.1 필터링
* ***filter - Predicate를 통한 필터링***
~~~java
Stream<T> filter(Predicate<? super T> predicate);
~~~
* ***distinct - 고유 요소만 필터링***
~~~java
Stream<T> distinct();
~~~
</br>
</br>

## 5.2 스트림 슬라이싱
### 프레디케이트를 이용한 슬라이싱
> **예제코드**:  <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter05/SourceCode052.java">SourceCode052</a>

 * ***takeWhile***: Predicate의 결과가 true인 요소에 대한 필터링. Predicate이 처음으로 거짓이 되는 지점에 연산을 멈춘다.
   * <code> Stream<T> takeWhile(Predicate<? super T> predicate) </code>
 * ***dropWhile***: Predicate의 결과가 false인 요소에 대한 필터링. Predicate이 처음으로 거짓이 되는 지점까지 발견된 요소를 버린고 남은 요소반환 (takeWhile과 정반대의 작업)
    * <code> Stream<T> dropWhile(Predicate<? super T> predicate)</code>

 ~~~java

Stream.of(1,2,3,4,5,6,7,8,9)
                .filter(n -> n%2 == 0)
                .forEach(System.out::println);
/*filter 결과: 2,4,6,8, */

Stream.of(2,4,3,4,5,6,7,8,9)
        .takeWhile(n -> n%2 == 0)
        .forEach(System.out::println);
/*takeWhile 결과: 2,4 */

Stream.of(2,4,3,4,5,6,7,8,9)
        .dropWhile(n -> n%2 == 0)
        .forEach(System.out::println);
/*dropWhile 결과: 3,4,5,6,7,8,9 */
 ~~~

### 스트림 축소
***limit*** - 주어진 값 이하의 크기를 갖는 새로운 스트림을 반환한다.  
~~~java
Stream<T> limit(long maxSize)
~~~
<code>limit(3) -> 프레디케이트와 일치하는 처음 3번째 요소 반환 </code>

### 요소 건너뛰기
***skip*** - 처음 n개 요소를 제외한 스트림을 반환한다. 
~~~java
Stream<T> skip(long n);
~~~

</br>
</br>

## 5.3 매핑
특정 객체에서 특정 데이터를 선택하는 작업. 인수로 제공된 함수는 각 요소에 적용되며 함수를 적용한 결과가 새로운 요소로 매핑된다.  
(이과정은 기존의 값을 '고친다'라는 개념보다 '새로운 버전을 만든다'라는 개념에 가까워, 변환에 가까운 **매핑**이라는 단어를 사용한다.)

### 스트림의 각요소에 함수 적용하기
* map - 함수를 인수로 받아 새로운 요소로 매핑된 스트림을 반환한다. 기본형 요소에 대한 mapToType 메서드도 지원한다 (mapToInt, mapToLong, mapToDouble).
~~~java
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
~~~
### 스트림 평면화
* ***flatMap*** : 각 배열을 스트림이 아니라 스트림의 콘텐츠로 매핑한다. 스트림의 각 값을 다른 스트림으로 만든다음에 모든 스트림을 하나의 스트림으로 연결하는 기능을 수행한다. 
> <a href= "https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter05/SourceCode053.java"> 예제코드 </a> : SourceCode053

> ![](./img/chapter053.png)

</br>
</br>

## 5.4 검색과 매칭
* ***anyMatch*** - 적어도 한 요소와 일치하는지 확인하는 최종 연산이다 (일치하는 순간 true 반환).
~~~java
boolean anyMatch(Predicate<? super T> predicate);
~~~
* ***allMatch*** - 모든 요소와 일치하는지 검사하는 최종 연산이다 (일치하지 않는 순간 false 반환).
~~~java
boolean allMatch(Predicate<? super T> predicate);
~~~
* ***noneMatch*** - 모든 요소가 일치하지 않는지 검사하는 최종 연산이다 (일치하는 순간 false 반환).
~~~java
boolean noneMatch(Predicate<? super T> predicate);
~~~


> anyMatch, allMatch, noneMatch 세 메서드는 스트림 ***쇼트서킷*** 기법, 즉 자바의 &&, ||와 같은 연산을 활용한다.

>> 쇼트서킷 평가  
  전체 스트림을 처리하지 않았더라도 결과를 반환할수 있다. 예를들어 and 연산으로 연결된 커다란 boolean 표현식을 평가한다고 할때, 표현식에서 하나라도 거짓이라는 결과가 나오면 나머지 표현식의 결과와 상관없이 전체 결과도 거짓이 된다. 

* ***findFirst*** - 첫 번째 요소를 찾아 반환한다. 순서가 정해져 있을 때 사용한다.
~~~java
Optional<T> findFirst();
~~~

* ***findAny*** - 요소를 찾으면 반환한다. 요소의 반환순서가 상관없을 때 findFirst 대신 사용된다.
~~~java
Optional<T> findAny();
~~~

> **Optional** 이란?  
값의 존재나 부재 여부를 표현하는 컨테이너 클래스이다. 값이 존재하는지 확인하고 값이 없을때 어떻게 처리할지 강제하는 기능을 제공한다. 
> * isPresent() : 값을 포함하면 true반환, 포함하지않으면 false반환
> * ifPresent(Consumer<T> block) : 값이 있으면 주어진 블록실행, T인수를 받으며 void반환
> * T get() : 값이 존재하면 값을 반환, 값이없으면 NoSuchElementException 발생
> * T orElse(T other) : 값이 있으면 값을 반환, 값이없으면 기본값 반환

</br>
</br>

## 5.5 리듀싱
***리듀싱연산***은 모든 스트림 요소를 처리해서 값으로 도출하는 것을 의미한다. 함수형 프로그래밍 용어로는 이 과정ㅇ이 마치 종이를 작은 조각이 될때 까지 반복해서 접는것과 비슷하다는 의미로 폴드라고 부른다.

### 요소의 합
for-each를 이용한, 리스트의 숫자더하기에서는 파라미터를 두개사용했다. reduce를 이용하면 애플리케이션의 반복된 패턴을 추상화 할수 있다.

reduce는 두개의 인수를 갖는다. 
* 초기값()
* 두 요소를 조합해서 새로운 값을 만드는 BinaryOperator\<T>, 예제에서는 람다포현식 (a,b) -> a+b를 사용했다.
~~~java
T reduce(T identity, BinaryOperator<T> accumulator);
~~~

reduce로 다른람다를 넘겨주면 모든 요소에 곱셈을 적용할 수 있다.
~~~java
int sum_reduce = numbers.stream().reduce(1, (a,b) -> a * b);
~~~
~~~java
/*reduce의 연산과정*/
      [4]   [5]  [3]  [9]  ---- Stream<Integer>
       ↓     |    |
0  ->  +     ↓    |        ---- reduce(0, (a,b) -> a + b)
       4  -> +    ↓ 
             9 -> +
                  12
~~~
람다의 첫번째 파라미터(a)에 0이 사용되었고 스트림에서 4를 소비해서 두번째 파라미터(b)로 사용했다. 0+4의 결과인 4가 새로운 누적값이 되었다. 이제 누적값으로 람다를 다시 호출하며 다음요소인 5를 소비한다. 

### 초기값 없음
초기값을 받지 않도록 오버로드된 reduce도 있다. 이 reduce는 Optional 객체를 반환한다.
<code>Optional\<T></code>를 반환하는 이유는 아무요소도 없는 상황이 있다. reduce는 합계가 없음을 가리킬수 있도록 Optional객체로 감싼 결과를 반환하다.
~~~java
Optional<T> reduce(BinaryOperator<T> accumulator);
~~~

### 최댓값과 최솟값
reduce는 두개의 인수를 갖는다. 
 * 초깃값
 * 스트림의 두 요소를 합쳐서 하나의 값으로 만드는데 사용할 람다.
 ~~~java
Optional<T> max(Comparator<? super T> comparator);
Optional<T> min(Comparator<? super T> comparator);
 ~~~


 > **예제코드**:  <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter05/SourceCode055.java">SourceCode055</a>


</br>
</br>

 ### ✍ reduce 메서드의 장점과 병렬화
> reduce를 이용하면 내부 반복이 추솽화 되면서 내부 구현에서 병렬로 reduce를 실행한다. 반복적인 합계에서는 sum 변수를 공유해야 하므로 쉽게 병렬화하기 어렵다. 스트림은 내부적으로 포크/조인 프레임워크(fork/join framework)를 통해 이를 처리한다. 7장에서는 스트림의 모든 요소를 더하는 코드를 병렬로 만드는 방법도 설명한다. stream()을 parallelStream()으로 바꾸면된다.
### ✍ 스트림 연산 : 상태없음과 상태있음
> map,filter 등은 입력 스트림에서 각 요소를 결과를 출력스트림으로 보낸다. 따라서 이들은 보통 상태가 없는, 즉 내부상태를 갖지 않는 연산이다.  
reduce,sum,max 같은 연산은 결과를 누적할 내부상태가 필요하다. 스트림에서 처리하나 요소 수와 관계없이 내부상태의크기는 한정<sup>bounded: 바운드</sup> 되어있다.  
sorted,distinct 같은 연산은 요소를 정렬하거나 제거하려면 과거의 이력을 알고 있어야한다. 어떤 요소를 출력스트림으로 추가하려면 **모든 요소가 버퍼에 추가 되어있어야한다**. 연산을 수행하는 데이터가 무한이라면 문제가 생길 수 있다. 이러한 연산을 내부상태를 갖는 연산이라고 한다. 
## 5.6 실전연습
**실전연습 코드**:  <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter05/SourceCode056.java">SourceCode056</a>

## 5.7 숫자형 스트림
스트림 API는 숫자 스트림을 효율적으로 처리할 수 있도록 기본형 특화 스트림(primitive stream specialization)을 제공한다.

### 기본형 특화 스트림
기본형 특화 스트림으로 IntStream, DoubleStream, LongStream이 존재하며 각각의 인터페이스에는 숫자 스트림의 합계를 계산하는 sum, 최댓값 요소를 검색하는 max 같이 자주 사용하는 숫자 관련 리듀싱 연산 메서드를 제공한다.

### 숫자스트림으로 매핑
스트림을 특화 스트림으로 변환할때는 mapToInt, mapToDuble, mapToLong 세가지 메서드를 가장많이 사용한다. 
~~~java
int calories = menu.stream()
               .mapToInt(Dish::getCalories) <-IntStream반환
               .sum();
~~~
mapToInt 메서드는 각 요리에서 모든 칼로리(Integer)를 추출한 뒤 InsTream을 반환한다.(Stream<Integer>가 아님) 스트림이 비어있으면 sum은 기본값0을 반환한다.

### 객체 스트림으로 복원하기
숫자 스트림을 만든 다음에, 원상태인 특화되지 않은 스트림으로 복원할수 있다. **boxed**메서드를 이용해서 특화스트림을 일반 스트림으로 변환할수 있다.
~~~java
IntStream intStream = menu.stream.mapToInt(Dish::getCalories) ;
Stream<Integer> stream = intStream.boxed(); <-숫자스트림을 스트림으로 변환
~~~
### 기본값: optionalInt
Optional도 기본형에 대하여 지원한다. OptionalInt, OptionalDouble, optionalLong 세 가지 기본형 특화 스트림 버전의 Optional이 제공된다.

### 숫자 범위
특정 범위의 숫자를 이용해야 할 때 range와 rangeClosed 메서드를 사용할 수 있다. 이는 IntStream, LongStream 두 기본형 특화 스트림에서 지원된다. range는 열린 구간을 의미하며, rangeClosed는 닫힌 구간을 의미한다.
</br>
</br>

## 5.8 스트림 만들기

### (1) 값으로 스트림 만들기
정적 메서드 Stream.of 을 이용하여 스트림을 만들 수 있다.

### (2) null이 될 수 있는 객체로 스트림 만들기
자바 9부터 지원되며 Stream.ofNullable 메서드를 이용하여 null이 될 수 있는 객체를 지원하는 스트림을 만들 수 있다.

### (3) 배열로 스트림 만들기
배열을 인수로 받는 정적 메서드 Arrays.stream 을 이용하여 스트림을 만들 수 있다.

### (4) 파일로 스트림 만들기
파일을 처리하는 등의 I/O 연산에 사용하는 자바의 NIO API(비블록 I/O)도 스트림 API를 활용할 수 있도록 업데이트되었다. java.nio.file.Files의 많은 정적 메서드가 스트림을 반환한다. 예를 들어 Files.lines는 주어진 파일의 행 스트림을 문자열로 반환한다.

### (5) 함수로 무한 스트림 만들기
Stream.iterate와 Stream.generate를 통해 함수를 이용하여 무한 스트림을 만들 수 있다. iterate와 generate에서 만든 스트림은 요청할 때마다 주어진 함수를 이용해서 값을 만든다. 따라서 무제한으로 값을 계산할 수 있지만, 보통 무한한 값을 출력하지 않도록 limit(n) 함수를 함께 연결해서 사용한다.
> Stream.iterate
~~~
public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
~~~
> Stream.generate
~~~
public static<T> Stream<T> generate(Supplier<T> s)
~~~