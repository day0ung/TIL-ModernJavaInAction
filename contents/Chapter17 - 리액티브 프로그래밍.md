# 리액티브 프로그래밍
리액티브 프로그래밍 패러다임의 중요성이 증가하는 이유

* 빅데이터 : 페타바이트 단위로 구성되며 계속해서 증가
* 다양한 환경 : 모바일 디바이스에서 클라우드 기반 클러스터에 이르기까지
* 사용 패턴 : 사용자는 24시간 서비스를 이용할 수 있으며 밀리초 단위의 응답을 원함  

다양한 시스템과 소스에서 들어오는 데이터 항목 스트림을 비동기적으로 처리하고 합쳐서 문제를 해결해야한다.
## 17.1 리액티브 매니패스토
리액티브 프로그래밍의 핵심 원칙
* 반응성 : 빠르면서 일정하고 예상할 수 있는 반응 시간을 제공한다.
* 회복성 : 장애가 발생해도 시스템은 반응성은 유지된다.
* 탄력성 : 무거운 작업 부하가 발생하면 자동으로 컴포넌트에 할당된 자원 수를 늘린다.
* 메시지 주도 : 컴포넌트 간의 약한 결합, 고립, 위치 투명성이 유지되도록 시스템은 비동기 메시지 전달에 의존한다.

#### 애플리케이션 수준의 리액티브
* 이벤트 스트림을 블록하지 않고 비동기로 처리하는 것이 멀티코어 CPU의 사용률을 극대화할 수 있는 방법이다.
이를 위해 스레드를 퓨처, 액터, 일련의 콜백을 발생시키는 이벤트 루프 등과 공유하고 처리할 이벤트를 관리한다.
* 개발자 입장에서는 저수준의 멀티 스레드 문제를 직접 처리할 필요가 없어진다.
* 이벤트 루프 안에서는 절대 동작을 블락하지 않는다는 전제조건이 따른다. (데이터베이스, 파일 시스템 접근, 원격 소비스 호출 등 I/O 관련 동작 등등)
* 비교적 짧은 시간동안만 유지되는 데이터 스트림에 기반한 연산을 수행하며, 보통 이벤트 주도로 분류된다.

#### 시스템 수준의 리액티브
* 여러 애플리케이션이 한개의 일관적이고 회복할 수 있는 플랫폼을 구성할 수 있게 해준다.
* 애플리케이션 중 하나가 실패해도 전체 시스템은 계속 운영될 수 있도록 한다.
* 애플리케이션을 조립하고 상호소통을 조절한다. (메시지 주도)
* 컴포넌트에서 발생한 장애를 고립시킴으로 문제가 다른 컴포넌트로 전파되면서 전체 시스템 장애로 이어지는 것을 막는다.(회복성)
* 모든 컴포넌트는 수신자의 위치와 상관 없이 다른 모든 서비스와 통신할 수 있는 위치 투명성을 제공한다. 이를 통해 시스템을 복제할 수 있으며 작업 부하에 따라 애플리케이션을 확장할 수 있다.(탄력성)


> **애플리케이션 수준의 리액티브 vs 시스템 수준의 리액티브**  
> 애플리케이션 수준의 리액티브란 리액티브 프로그래밍을 말한다. 주로 비동기로 작업을 수행하여 최신 멀티코어 CPU 사용율을 극대화 한다.  
> 시스템 수준의 리액티브란 리액티브 시스템을 말한다. 리액티브 시스템이란 여러 애플리케이션이 한 개의 일관적인, 회복할 수 있는 플랫폼을 구성할 수 있게 해줄 뿐 아니라 이들 애플리케이션 중 하나가 실패해도 전체 시스템은 계속 운영될 수 있도록 도와주는 소프트웨어 아키텍쳐다.

## 17.2 리액티브 스트림과 플로 API
리액티브 스트림은 잠재적으로 무한의 비동기 데이터를 순서대로 그리고 블록하지 않는 역압력을 전제해 처리하는 표준 기술이다.

> 역압력 : 이벤트를 제공하는 속도보다 느린 속도로 이벤트가 소비되면서 문제가 발생하는 것을 막는 장치
####  Flow 클래스 소개
자바 9에서는 리액티브 프로그래밍을 제공하는 클래스 java.util.concurrent.Flow를 추가했다.
이 클래스는 정적 컴포넌트 하나만 포함하고 있으며 인스턴스화할 수 없다.  

**Flow 클래스의 인터페이스**  
Publisher가 항목을 발행하면 Subscriber가 한 개 또는 여러개씩 항목을 소비하는데 Subscription이 이 과정을 관리할 수 있도록 FLow 클래스는 관련된 인터페이스와 정적 메서드를 제공한다.
* [Publisher](#publisher)
* [Subscriber](#subscriber)
* [Subscription](#subscription)
* [Processor](#processor)

#### Publisher
함수형 인터페이스로 구독자를 등록할 수 있다.
```java
//Publisher가 발행한 리스너로 Subscriber에 등록할 수 있다.
@FunctionalInterface
public interface Publisher<T> {
    void subscribe(Flow.Subscriber<? super T> s);
}
```
#### Subscriber
구독자이며 프로토콜에서 정의한 순서로 지정된 메서드 호출을 통해 발행되어야 한다.
```java
//Publisher가 관련 이벤트를 발행할 때 호출할 수 있도록 콜백 메서드 네 개를 정의
public interface Subscriber<T> {
  void onSubscribe(Flow.Subscription s);
  void onNext(T t);
  void onError(Throwable t);
  void onComplete();
}
```
#### Subscription
구독자와 발행자 사이 관계를 조절하기 위한 인터페이스이다. request는 처리할 수 있는 이벤트의 개수를 전달하며, cancel은 더 이상 이벤트를 받지 않음을 통지한다.
```java
public interface Subscription {
    void request(long n); //publisher에게 이벤트를 처리할 준비가 되었음을 알림
    void cancel();//publisher에게 이벤트를 받지 않음을 통지
}
```
#### Processor
구독자이며 발행자이다. 주로 구독자로써 전달받은 이벤트를 변환하여 발행하는, 이벤트를 변환하는 역할을 수행한다.
```java
//리액티브 스트림에서 처리하는 이벤트의 변환단계를 나타냄
//에러나 Subscription 취소 신호 등을 전파
public interface Processor<T, R> extends Flow.Subscriber<T>, Flow.Publisher<R> { }
```

**인터페이스 구현 규칙**
* Publisher는 반드시 Subscription의 request 메서드에 정의된 개수 이하의 요소만 Subscriber에 전파해야 한다.
* Subscriber는 요소를 받아 처리할 수 있음을 Publisher에 알려야 한다. 이를 통해 역압력을 행사할 수 있다.
* Publisher와 Subscriber는 정확하게 Subscription을 공유해야한다. 그러려면 onSubscribe와 onNext 메서드에서 Subscriber는 request 메서드를 동기적으로 호출할 수 있어야 한다.

📌 ***첫 번째 리액티브 애플리케이션 만들기***  

> **예제코드**:  
> * <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter17/TempInfo.java">TempInfo</a>  - 현재 보고된 온도를 전달하는 자바빈
> * <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter17/TempSubscription.java">TempSubscription</a> - Subscriber에게 TempInfo 스트림을 전송하는 Subscription 
> * <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter17/TempSubscriber.java">TempSubscriber</a> - 받은 온도를 출력하는 Subscriber
> * <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter17/Main.java">Main</a> - Publisher를 만들고 TempSubscriber를 이용해 Publisher에 구독하도록 구현
> * <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter17/TempProcessor.TempProcessor">Main</a> - 화씨를 섭씨로 변환하는 Processor


## 17.3 리액티브 라이브러리 RxJava사용하기 
* 넷플릭스에서 개발한 라이브러리로 자바에서 리액티프 애플리케이션을 구현하는 데 사용한다.
* RxJava는 Flow.Publisher를 구현하는 두 클래스를 제공한다.
  * io.reactivex.Flowable : 역압력을 지원하는 Flow
  * io.reactivex.Observable : 역압력을 지원하지 않는 Flow.
* 천 개 이하의 요소를 가진 스트림이나 마우스 움직임, 터치 이벤트 등 역압력을 적용하기 힘든 GUI 이벤트, 자주 발생하지 않는 종류의 이벤트에는 역압력을 적용하지 말 것을 권장한다.

#### Observable 만들고 사용하기
Observable, Flowable 클래스는 다양한 종류의 리액티브 시스템을 편리하게 만들 수 있도록 여러 팩토리 메서드를 제공한다. just()와 interval() 팩토리 메서드를 사용하면 요소를 직접 지정해 이를 방출하도록 지정할 수 있다.
* Observable.just() :  팩토리 메서드는 한개 이상의 요소를 이용해 이를 방출하는 Observable로 변환
  * ```java
    Observable<String> strings = Observable.just("first", "second");
    ```
  * Observable의 구독자는 onNext("first"), onNext("second").onComplete()의 순서로 메시지를 받는다.  

* Observable.interval(): 사용자와 실시간으로 상호작용 하면서 지정된 속도로 이벤트를 방출하는 상황에서 유용하게 사용하는 메서드
    * ```java
      Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);
      ```
    * 0에서 시작해 1초 간격으로 long형식의 값을 무한으로 증가 시키며 값을 방출하는 Observable

***Observable 인터페이스***
```java
public interface Observer<T> {
	void onSubscribe(Disposable d);
	void onNext(T t);
	void onError(Throwable t);
	void onComplete();
}
```
#### Observable을 변환하고 합치기
자바 9 플로 API의 Flow.Processor는 한 스트림을 다른 스트림의 입력으로 사용할 수 있었다. 하지만 스트림을 합치고, 만들고, 거스는 등의 복잡한 작업을 구현하기는 매우 어려운 일이다. RxJava의 Observable 클래스는 앞서 언급한 복잡한 작업에 대하여 쉽게 처리할 수 있는 다양한 기능들을 제공한다.

이런 함수들을 documents의 설명으로만은 이해하기 상당히 어렵기에 마블 다이어그램이라는 시각적 방법을 이용해 이해를 돕는다. rxmarbles.com 사이트는 RxJava의 Observables의 스트림의 요소 위치를 직접 옮겨가며 결과를 확인 메서드의 동작을 마블 다이어그램으로 확인할 수 있도록 도와준다. documents와 함께 참고하자.