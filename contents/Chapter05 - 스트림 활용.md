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
특정 객체에서 특정 데이터를 선택하는 작업
</br>
</br>

## 5.4 검색과 매칭

</br>
</br>

## 5.5 리듀싱

</br>
</br>

## 5.6 숫자형 스트림

</br>
</br>

## 5.7 스트림 만들기
