# 자바 8,9,10,11: 무슨일이 일어나고있는가?

### 자바 8을 이용하면 자연어에 가깝게 간단한 방식으로 코드를 구현할수 있다.
* 스트림 처리 - 조립라인 처럼 스트림 API는 파이프라인을 만드는 데 필요한 많은 메서드를 제공한다. 또한 스레드라는 복잡한 작업을 사용하지 않으면서 공짜로 병렬성을 얻을 수 있다.
* 동작 파라미터화(behavior parameterization)로 메서드에 코드 전달하기 - 동작(메서드)을 파라미터화 해서 전달 가능하다. 함수형 프로그래밍 기술을 이용한다.
* 병렬성과 공유 가변 데이터 - 병렬성을 공짜로 얻을 수 있지만 이를 얻기 위해 포기해야 하는 점이 있다. 안전하게 실행할 수 있는 코드를 만들려면 **공유된 가변 데이터(shared mutable data)**에 접근하지 않아야 한다

## 1.2.2 스트림처리
스트림이란,  한번에 한개씩 만들어지는 연속적인 데이터 항목들의 모임이다.

자바에서는 많은 양의 데이터를 저장하기 위해서 배열이나 컬렉션을 사용하는데, 
이렇게 저장된 데이터에 접근하기 위해서는 반복문이나 반복자(iterator)를 사용하여 매번 새로운 코드를 작성해야 한다.

하지만 이렇게 작성된 코드는 길이가 너무 길고 가독성도 떨어지며, 코드의 재사용이 거의 불가능해
즉, 데이터베이스의 쿼리와 같이 정형화된 처리 패턴을 가지지 못했기에 데이터마다 다른 방법으로 접근해야만 하는 문제가 있었다

이러한 문제점을 극복하기 위해서 Java SE 8부터 스트림(stream) API를 도입했다

스트림 API의 핵심은 기존에는 한번에 한 항목을 처리했지만, 자바 8 에서는 작업을 (데이터베이스 질의처럼) 고수준으로 추상화 해서 일련의 스트림으로 만들어 처리할수 있다.

또한 스트림 *파이프라인을 이용해서 입력 부분을 여러 CPU 코어에 쉽게 할당할 수 있다.

* *파이프 라인이란? 
컴퓨터 과학에서 파이프라인(영어: pipeline)은 한 데이터 처리 단계의 출력이 다음 단계의 입력으로 이어지는 형태로 연결된 구조

* 첫장에서 등장한 예제코드 : <a href = "https://github.com/day0ung/TIL-ModernJavaInAction/blob/main/java_code/modern_java/src/chapter01/SourceCode011.java"> SourceCode011 </a>
---

## 1.2.3 동작파라미터화로 메서드에 코드전달하기
 동작의 전달을 위해 익명 클래스를 만들고 메서드를 구현해서 넘길 필요 없이, 준비된 함수를 메서드 참조 ::를 이용해서 전달할 수 있다. 아래 예제를 통해 자바 8에서는 더 이상 메서드가 이급값이 아닌 일급값인것을 확인할 수 있다.



## 1.3.1 메서드와 람다를 일급시민으로
자바 8에서 함수 사용법은 일반적인 프로그래밍 언어의 함수 사용법과 아주 비슷하다. 프로그래밍 언어의 핵심은 <b>값을 바꾸는 것</b>이다. 전통적으로 프로그래밍 언어에서는 이 값을 일급(first-class) 값(또는 시민)이라고 부른다. 자바 프로그래밍 언어의 구조체 (메서드,클래스)는 값의 구조를 표현하는데 도움이 되지만, 구조체를 자유롭게 전달할 수 없다. 이렇게 전달할 수 없는 구조체는 이급시민이다.  

이전까지 자바 프로그래밍 언어에서는 기본값, 인스턴스만이 일급 시민이었다. 메서드와, 클래스는 이 당시 일급 시민이 아니었는데, 런타임에 메서드를 전달할 수 있다면, 즉 메서드를 일급 시민으로 만들면 프로그래밍에 유용하게 활용될 수 있다. 자바 8 설계자들은 이급 시민을 일급 시민으로 바꿀 수 있는 기능을 추가했다.

### :: 메서드 참조( methdo reference, 이값을 메서드 값으로 사용하라는 의미) 

디렉토리에서 모든 숨겨진 파일을 필터링 한다고 가정해봤을때, 
```java 
File[] hiddenFiles = new File(".").listFiles(new FileFilter()){
    public boolean accept(File file){
        return file.isHidden();  //숨겨진 파일 필터링
    }
}
```
위코드에서 File클래스에는 이미 isHidden이라는게 있는데 복잡하게 감싼다음에 filter를 인스턴스화 해야하는가? 자바 8에서는 아래처럼 구현이 가능하다!

```java 
File[] hiddenFiles = new File(".").listFiles(File::isHidden);
```
메서드 참조 :: 를 이용해서 listFiles에 직접 전달할 수 있다.


람다 : 익명함수</br>
자바 8에서는 메서드를 일급값으로 취급하며 람다를 포함하여 함수도 값으로 취급할수 있다.


## 1.3.2 코드 넘겨주기 : 예제
Apple 클래스와 getColor메서드가 있고, Apples 리스트를 포함하는 변수  inventory가 있다고 가정</br>
이때, 모든 녹색사과를 선택해서 리스트를 반환하려는 프로그램 구현</br>

* Predicate란?(프레디케이트)
    *  예제에서 Aplle::isGreenApple 메서드를 filterApples로 넘겨주었다.</br>
     수학에서는 인수로 값을 받아 true/false를 반환하는 함수를 프레디케이트라 한다.</br>
     Function<Apple, Boolean>과 같이 코드를 구현할 수 있지만 Predicate< Apple > 을    사용하는것이 더 표준적인방식이며, 더 효율적이다.

## 1.3.3 메서드 전달에서 람다로 : 예제
메서드를 값으로 전달하는것은 유용한 기능이지만, isHeavyApple, isGreen 처럼 한두번만 사용할 메서드를 매번 정의하는 것은 귀찮은 일이다. 자바 8에서는 <b>람다</b>라는 새로운 개념을 이용해서 코드구현이 가능하다
* 예제코드 :  <a href = "https://github.com/day0ung/TIL-ModernJavaInAction/blob/main/java_code/modern_java/src/chapter01/SourceCode013.java"> SourceCode013 </a>
## 1.4 스트림
1.3 코드예제에서 filterApples를 사용했다. 라이브러리 메서드 <b> filter</b>를 이용하면 filterApples 메서드를 구현할 필요가 없다.
예를들어 리스트에서 고가의 트랜잭션(거래)만 필터링한 후에 통화로 결과를 그룹해야한다면
```java 
Map<Currency, List<Transaction>> map = new HashMap<>();
for(Transaction tran : trans){ //리스트반복
    if(tran.getPrice() > 1000){ //필터링
        Currency currency = tran.getCurrency(); //통화추출
        List<Transaction> tranforCurency = map.get(currency);
        if(tranforCurency == null){
            //그룹화된 맵에 항목이없으면 새로 만든다.
            tranforCurency = new ArrayList<>();
            map.put(currency, tranforCurency)
        }
        tranforCurency.add(tran) //리스트에 추가 
    }
    
}
```
위코드는 구현해야하는 기본코드가 많고, 제어흐름이 문장이많아 이해하기 어렵다.</br>
스트림 API를 이용하면 아래처럼 간단하다.
```java 
import static java.util.stream.Collectors.groupingBy;

Map<Currency, List<Transaction>> map = 
    tran.stream()
        .filter((Transaction t) -> t.getPrice() > 1000) //필터링
        .collect(groupingBy(Transaction::getCurrency)); //통화로 그룹화
```
스트릠 API를 활용하면 컬렉션 API와 다른 방식으로 데이터를 처리한다.

컬렉션에서는 반복과정을 직접 처리해야했다. for-each사용하면서 작업을 수행</br>
이런것을 **(외부반복)** 이라한다.

스트림을 이융하면 라이브러리 내부에서 모든데이터가 처리된다.</br>
이런것을 **(내부반복)** 이라한다.  
  


### 1.4.1 멀티스레딩은 어렵다
이전 자바 버전에서 제공하는 스레드 API로 멀티스레딩 코드를 구현해서 병렬성을 이용하는것은 쉽지않다.  각각의 스레더는 동시에 공유된 데이터에 접근하고 데이터를 갱신하는데 
스레드를 잘 제어하지 못하면 원치않게 데이터가 바뀔수 있다. 또한 멀티스레딩 모델은 순차적인 모델보다 다루기가 어렵다.  
<sup>(전통적으로 멀티스레딩에서는 synchronized를 활용하지만, 미묘한 버그가 발생할 수 있다)<sup>  
  
자바 8에서는 스트림 API(java.util.stream)는 컬렉션을 처리하면서 발생하는 모호함과 반복적인 코드문제 , 멀티코어 활용 어려움이라는 두가지 문제를 모두 해결했다.  
예를들어 두 CPU를 가진 환경에서 리스트를 필터링할 때 한 CPU는 리스트의 앞부분을 처리하고, 다른 CPU는 뒷부분을 처리하도록 요청할수 있다.  
이과정을 **포킹단계**라고 한다. 
1. 포크 [A, B, C, || D, E]  <sup>5개의 사과 리스트
2. 필터 [ B, C ]<sup>cpu1</sup>   [ D ]<sup>cpu2</sup> 
3. 결과합침 [B,C,D]

컬렉션은 어떻게 데이터를 저장하고 접근할지에 중점을 두는반면,   
스트림은 데이터에 어떤 계산을 할것인지 묘사하는것에 중점을 둔다. 스트림은 스트림내의 요소를 쉽게 병렬로 처리할수 있는 환경을 제공한다는것이 핵심이다  
<sup>컬렉션을 필터링 하는 빠른방법은, 컬렉션을 스트림으로 바꾸고, 병럴처리 후 리스트로 다시 복원하는것이다</sup>  
~~~java
import static java.util.stream.Collectors.toList;

//순차처리 방식의 코드
List<Apple> heavyApples = inventory.stream()
                          .filter((Apple a) -> a.getWeight() > 150)
                          .collect(toList());

//병렬처리 방식의 코드
List<Apple> heavyApples = inventory.parallelStream()
                          .filter((Apple a) -> a.getWeight() > 150)
                          .collect(toList()); 
~~~

## 1.5 디폴트 메서드와 자바 모듈
요즘은 외부에서 만들어진 컴포넌트를 이용해 시스템을 구축하는 경향이 잇다. 거기에 패키지의 인터페이스를 바꿔야하는 상황에서는 인터페이스를 구현하는 모든 클래스의 구현을 바꿔야한다.  
자바 9의 모듈 시스템은 모듈을 정의하는 문법을 제공한다. 모듈 덕분에 jar같은 컴포넌트에 구조를 적용할수 있다.(14장에서..자세히)    
자바 8에서는 인터페이스를 쉽게 바꿀수 있도록 **디폴트메서드**를 제공한다. 1.4절에서의 예제코드 (순차,병렬처리방식 stream(),parallelStream())는 자바 8 이전에는 컴파일 할수 없는 코드다.  
List<T>, Collection<T>는 위 메서드를 지원하지 않기 때문에 직접 인터페이스를만들어서 직접구현해야한다. 이렇게되면, 이미 컬렉션 API의 인터페이스를 구현하는 많은 컬렉션프레임워크가 존재하는데 이것을 사용하는 모든 클래스에 구현한 메서드를 추가해야한다.<sup>매우 귀찮은 일..</sup>  
자바 8은 구현클래스에서 구현하지 않아도 되는 메서드를 인터페이스에 추가할수 있는 기능을 제공한다. 메서드본문<sup>bodies</sup>은 클래스 구현이 아니라 인터페이스의 일부로 포함된다.  
그래서 이를 **디폴트메서드**라고 부른다. 디폴트메서드를 이용하면 기존 코드를 건드리지않고도 원래의 인터페이스 설계를 자유롭게 확장할 수 있다. 자바 8에서는 List에 직접 sort메서드를 호출할 수 있다. 이는 자바8 인터페이스에 디폴트메서드 정의가 추가되었기 때문이다.  
~~~java
default void sort(Comarator<? super E> c){
    Collections.sort(this,c);
}
~~~  
<sup>이 디폴트메서드는 정적메서드인 Collections.sort를 호출한다  

## 1.6 함수형 프로그래밍에서 가져온 다른 유용한 아이디어
자바 8에서는 NullPointer 예외를 피할수 있도록 도와주는 Optional<T> 클래스를 제공한다. Optional<T>는 값이 없는 상황을 어떻게 처리할지 명시적으로 구현하는 메서드를 포함하고있다. 따라서 이것을 사용하면 NullPointer예외를 피할 수 있다. <sup>(11장에서 자세히..설명)



