# OOP와 FP의 조화 : 자바와 스칼라 비교
스칼라는 객체지향과 함수형 프로그래밍을 혼합한 언어다. 스칼라 또한 JVM에서 실행되며 자바에 비해 더 다양하고 심화된 함수형 기능을 제공한다. 스칼라와 자바에 적용된 함수형의 기능을 살펴보면서 자바의 한계가 무엇인지 확인해보자.

## 20.1 스칼라 소개
스칼라 코드는 자바와 비슷지만 자바 코드보다 간결한다.
* 명령형 스칼라
  * ```java
        object Beer {
            def main(args: Array[String]){
                var n : Int = 2
                while( n <= 6){
                    println(s"Hello ${n} bottles of beer")
                    n += 1
                }
           }
        }
    // ${n} : 화면에 n값을 출력한다. 이것은 스칼라의 *문자열 보간법*이라는 기능이다. 
    // 문자열 보간법: 문자열 자체에 변수화 표현식을 바로 삽입하는 기능
    ``` 
* 함수형 스칼라
   * ```
       object Beer {
           def main(args: Array[String]){
             2 to 6 foreach { n => println(s"Hello ${n} bottles of beer") }
           }
       }
     ``` 

스칼라 에서는 **모든 것**이 객체이다. 자바와 달리 스칼라에는 기본형이 없다. 

#### 기본자료구조: 리스트 ,집합, 맵, 튜플, 스트림, 옵션
* 컬렉션 만들기 
  * `val authorsToAge = Map("Raoul" -> 23, "Mario" -> 40, "Alan" -> 53)`
  *  -> 라는 문법으로 키를 값에 대응시켜 맵을 만든다
     *   ```java
         //자바에서맵을 만들때 
          Map<String, Integer> authorsToAge = new HashMap<>();
          authorsToAge.put("Raoul", 23);
          authorsToAge.put("Mario", 40);
          authorsToAge.put("Alan", 53);
         //java8
         Map<String, Integer> authorsToAge
            = Map.ofEntries(entry("Raoul", 23),
           entry("Mario", 40),
         ```
     
  * `val authors = List("Raoul", "Mario", "Alan")` 
  * `val numbers = Set(1, 1, 2, 3, 5, 8)` 

지금까지 만든 컬렉션은 기본적으로 **불변**이다.  
일단 컬렉션을 만들면 변경할수 없다. 
* 튜플 : 자바에서는 튜플을 지원하지않는다. 스칼라는 **튜플 축약어**, 간단한 문법으로 튜플을 만들수 있는 기능을 제공한다.
  * `val raoul = ("Raoul", "+44 7700 700042")`
* 스트림 : 스칼라의 스트림은 이전 요소가 접근할 수 있도록 기존 계산값을 기억한다. 또한 인덱스를 제공한다.
* 옵션 : 스칼라의 Option은 자바의 Optional과 같은 기능을 제공한다. 

## 20.2 함수
스칼라의 함수는 어떤 작업을 수행하는 일련의 명령어 그룹이다. 스칼라의 함수는 일급값이다.
```scala
def isJavaMentioned(tweet: String) : Boolen = tweet.contains("Java")
def isShortTweet(tweet: String) : Boolean = tweet.lenght < 20
```
#### 익명함수와 클로저
***익명함수***
스칼라는 익명 함수의 개념을 지원한다. 스칼라는 람다 표현식과 비슷한 문법을 통해 익명 함수를 만들 수 있다.
```java
//트윗이 긴지를 확인하는 익명함수를 isLongTweet라는 변수로 할당하는 예제
val isLongTweet : String => Boolean
 = (tweet : String) => tweet.length() > 60 
```
자바에서는 람다 표현식을 사용할수 있도록 Predicate, Function, Consumer등의 내장함수형 인터페이스를 제공한다.  
스칼라는 마찬가지로 **트레이트**를 지원한다. 

***클로저***  
클로저(closure)란 함수의 비지역 변수를 자유롭게 참조할 수 있는 함수의 인스턴스를 가리킨다. 자바의 람다 표현식에는 람다가 정의된 메서드의 지역 변수를 고칠 수 없다는 제약이 있으며, 암시적으로 final로 취급된다.  
즉, **람다는 변수가 아닌 값을 닫는다**는 사실을 기억해야한다. 
#### 커링
19장에서 커링이라는 기법을 설명했다. 커링은 x와 y라는 두 인수를 받는 함수 f를 한개의 인수를 받는 g라는 함수로 대체하는 기법이다.  
스칼라에서는 기존 함수를 쉽게 커리할 수 있는 방법을 제공한다.  
두 정수를 곱하는 간단한 메서드
* 자바사용
```java
// 전달된 모든 인수를 사용
static int multiply(int x, int y) {
    return x * y;
}
int r  = multiply(2, 10);

// 다른 함수를 반환하도록 multiply메서드 분할
static Function<Integer, Integer> multiplyCurry(int x) {
        return (Integer y) -> x * y;
}

//multiplyCurry가 반환하는 함수는 x와 인수 y를 곱한값을 캡쳐한다.
Stream.of(1, 3, 5, 7)
        .map(multiplyCurry(2))
        .forEach(System.out::println);
map과 multiplyCurry를 연결해서 각 요소에 2를 곱한다. 
```
* 스칼라사용
```java
def multiply(x : Int, y: Int) = x * y
val r = multiply(2, 10)

def multiplyCurry(x :Int)(y : Int) = x * y  //커리된 함수 정의
val r = multiplyCurry(2)(10) //커리된 함수 호출
```
스칼라는 커링을 자동으로 처리하는 특수 문법을 제공한다. 그렇기에 커리된 함수를 직접 만들어 제공할 필요가 없다.

## 20.3 클래스와 트레이트
스칼라의 클래스와 인터페이스는 자바에 비해 더 유연함을 제공한다. 

* 클래스  
스칼라에서는 생성자, 게터, 세터가 암시적으로 생성되므로 코드가 단순해진다.
```java
class Student(var name: String, var id: Int) //Student객체 초기화 
val s = new Student("Raoul", 1)
println(s.name) //이름을 얻어 Raoul출력
s.id = 1337 // id설정 
println(s.id) // 1337출력
```
* 트레이트  
스칼라의 트레이트는 자바의 인터페이스를 대체한다. 트레이트는 인터페이스와 달리 구현 가능하며 하나의 부모클래스를 갖는 상속과 달리 몇 개라도 조합해 사용 가능하다. 또한 인스턴스화 과정에서도 조합할 수 있다.
```java
trait Sized {
	var size : Int = 0
	def isEmpty() = size == 0 //기본 구현을 제공하는 메서드
}
```
객체 트레이트는 **인스턴스화 과정에서도 조합**할수 있다. 
Box클래스를 만든다음에 어떤 Box인스턴스는 트레이트 Sized가 정의하는 동작을 지원하도록 결정할수 있다. 
```java

class Box
val b1 = new Box() with Sized // 객체를 인스턴스화 할 때 트레이트를 조합함
println(b1.isEmpty()) // true
val b2 = new Box()
b2.isEmpty() // 컴파일 에러: Box 클래스 선언이 Sized를 상속하지 않음
```

Sized라는 트레이트는 size라는 가변 필드와 기본 구현을 제공하는 메서드 isEmpty를 포함한다. 
