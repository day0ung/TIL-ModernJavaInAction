# 동작 파라미터화 코드전달하기
* 변화하는 요구사항에 대응
* 동작파라미터화
* 익명클래스
* 람다 표현식 미리보기
* 실전예제

### 동작파라미터화를 이용하면 자주바뀌는 요구사항에 효과적으로 대응할 수있다.
동작 파라미터화란 아직은 어떻게 실행할 것인지 결정하지 않은 코드블록을 의미한다. 이 코드블록의 실행은 나중으로 미뤄진다. 결과적으로 코드블록에 따라 메서드의 동작이 파라미터화 된다. 

## 2.1.1 첫번째 시도: 녹색사과 필터링
사과 색을 정의하는 Color num이 존재한다고 가정했을때,
~~~java
enum Color {RED, GREEN}
~~~
* enum 이란?  
Enum은 열거형이라고 불리며, 서로 연관된 상수들의 집합을 의미하며,
기존에 상수를 정의하는 방법이였던 final static string 과 같이 문자열이나 숫자들을 나타내는 기본자료형의 값을 enum을 이용해서 같은 효과를 낸다.  

첫번째 시도한 코드는 녹색사과를 선택하는 데 필요한 조건을 가리킨다. 그런데 요구사항이 변화하며, **빨간** 사과도 필터링하게되었다. 이것을 고치려면 if문의 조건을 빨간사과로 바꾸는 선택을 할 수 있지만, 나중에 더 다양한 색 으로 필터링하는 등의 변화에는 적절하게 대응 할 수 없다.  

## 2.1.2 두번째 시도: 색을 파라미터화 
녹색사과뿐만아니라 다른색의 사과를 필터링할때 코드를 반복사용하지않고, 구현하기 위해서 메서드에 색을 파라미터화할수 있도록 메서드에 파라미터를 추가하면 변화하는 요구사항에 유연하게 대응하는 코드를 만들 수 있다. 또한 무게기준도 얼마든지 바뀔수 있다. 
~~~java
List<Apple> redApples = filterApplesByColor(inventory, Color.RED);

List<Apple> heavyApples = filterApplesByWeight(inventory, 100);
~~~
위 두 코드도 좋은 해결책이지만 <a href= "https://github.com/day0ung/TIL-ModernJavaInAction/blob/main/java_code/modern_java/src/chapter02/SourceCode021.java" >구현 코드 2.1.2 </a> 를 보면, 대부분 중복된다.  
이는 소프트웨어 공학의 DRY<sup> don't repeat yourself (같은것을 반복하지 말것)</sup> 원칙을 어기는 것이다.  

## 2.1.3 세번째 시도: 가능한 모든 속성으로 필터링
실전에서는 절대로 사용하지 말아야하는 코드, <a href= "https://github.com/day0ung/TIL-ModernJavaInAction/blob/main/java_code/modern_java/src/chapter02/SourceCode021.java" >구현 코드 2.1.3 </a>

~~~java
List<Apple> greenApples2 = filterApples(inventory, Color.GREEN, 0 ,true);
List<Apple> heavyApples2 = filterApples(inventory, null, 150 ,false);
~~~
true와 false는 뭘 의미하는지, 앞으로 요구사항이 바뀌었을대 유연하게 대응할수도 없다. 예를들어 사과의 크기,모양, 출하지 등으로 필터링하고 싶다면,.? filterApples에 어떤 기준으로 사과를 필터링할 것인지 효과적으로 전달할 수 있다면 더 좋을것이다.

## 2.2 동작파라미터화
