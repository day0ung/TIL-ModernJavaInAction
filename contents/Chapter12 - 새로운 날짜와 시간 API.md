# 새로운 날짜와 시간 API

### 자바 8 이전의 날짜와 시간 API의 문제들
* Date 클래스는 직관적이지 못하며 자체적으로 시간대 정보를 알고 있지 않다
* Date를 deprecated 시키고 등장한 Calendar 클래스 또한 쉽게 에러를 일으키는 설계 문제를 갖고 있다
* Date와 Calendar 두 가지 클래스가 등장하면서 개발자들에게 혼란만 가중되었다.
* 날짜와 시간을 파싱하는데 등장한 DateFormat은 Date에만 지원되었으며, 스레드에 안전하지 못했다.
* Date와 Calendar는 모두 가변 클래스이므로 유지보수가 아주 어렵다.

## 12.1 LocalDate, LocalTime, Instant, Duration, Period 클래스
java.time 패키지는 LocalDate, LocalTime, LocalDateTime, Instant, Duration, Period 등 새로운 날짜와 시간에 관련된 클래스를 제공한다.
* [LocalDate와 LocalTime 사용](#localdate와-localtime-사용)
* [날짜와 시간 사용](#날짜와-시간-사용)
* [Instant 클래스](#instant-클래스--기계의-날짜와-시간)
* [Duration과 Period 정의](#duration과-period-정의)

### LocalDate와 LocalTime 사용
***LocalDate***  
시간을 제외한 날짜를 표현하는 불변 객체다. LocalDate 객체는 어떤 시간대 정보도 포함하지 않는다. 정적 팩토리 메서드 of으로 LocalDate 인스턴스를 만들 수 있다.  
~~~java
LocalDate date = LocalDate.of(2020, 12, 22); // of
int year = date.getYear();
Month month = date.getMonth();
int day = date.getDayOfMonth();
LocalDate now = LocalDate.now(); // 현재 날짜 정보
~~~
 * LocalDate가 제공하는 get 메서드에 TemporalField를 전달해서 정보를 얻는 방법도 있다. TemporalField는 시간 관련 객체에서 어떤 필드의 값에 접근할지 정의하는 인터페이스다.  
 * ~~~java
    public int get(TemporalField field)
    int year = date.get(ChronoField.YEAR);
   
    //ChronoField는 TemporalField의 구현체이며 ChronoField의 열거자 요소를 이용해서 원하는 정보를 쉽게 얻을 수 있다.
    ~~~
 
***LocalTime***
시간에 대한 정보는 LocalTime 클래스로 표현할 수 있다. LocalTime도 정적 메서드 of로 인스턴스를 만들 수 있다.
~~~java
LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
int hour = time.getHour();
int minute = time.getMinute();
int second = time.getSecond();
~~~
* parse 메서드를 통해 날짜와 시간 문자열로 LocalDate와 LocalTime의 인스턴스를 만들 수 있다.
* ~~~java
  LocalDate date = LocalDate.parse("2020-12-22");
  LocalTime time = LocalTime.parse("13:45:20");
  ~~~
### 날짜와 시간 사용
LocalDateTime은 LocalDate와 LocalTime을 쌍으로 갖는 복합클래스이다.  
날짜와 시간을 모두 표현할수 있으며, 아래 코드처럼 직접 생성할수도 있고, 날짜와 시간을 조합하는 방법도 있다.
> > **예제코드** : <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter12/SourceCode121.java">SourceCode121.localdateTimeExample()</a>
### Instant 클래스 : 기계의 날짜와 시간
기계에서는 단위로 시간을 표현하기 어렵다(주,날짜,시간). java.time.Instant 클레서에서는 기계적인 관점에서 시간을 표현한다.  
팩토리 메서드에 ofEpochSecond에 초를 넘겨줘서 Instant클래스 인스턴스를 만들 수 있다. 해당클래스는 나노초(10억분의 1)의 정밀도를 제공한다. 
> > **예제코드** : <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter12/SourceCode121.java">SourceCode121.instantExample()</a>
~~~java
        int day = Instant.now().get(ChronoField.DAY_OF_MONTH);
~~~ 
위코드는 예외를 발생시킨다. Instant는 사람이 읽을 수 있는 시간 정보를 제공하지않는다. 
Instant에서는 Duration과 Period를 함께 활용할 수 있다. 

### Duration과 Period 정의
Temporal 인터스페이스는 특정 시간을 모델링하는 객체의 값을 어떻게 읽고 조작할지 정의한다.  
**Duration클래스** 의 정적 팩토리 메서드 between으로 두 시간 객체 사이의 지속시간을 만들 수 있다. 
~~~java
Duration d1 = Duration.between(time1, time2);
Duration d1 = Duration.between(dateTime1, dateTime2);
Duration d1 = Duration.between(instant1, instant2);
~~~

* LocalDateTime은 사람이 사용하도록, Instant는 기계가 사용하도록 만들어진 클래스로 서로 혼합할수 없다.   
* Duration클래스는 초와 나노초로 시간단위를 표현하므로 between메서드에 LocalDate를 전달할 수 없다.   


년, 월, 일로 시간을 표현할때는 **Peirod클래스** 를 사용한다. 
~~~java
Period tenDays = Period.between(LocalDate.of(2017, 9, 11), LocalDate.of(2017, 9, 21));   
~~~


## 12.2 날짜 조정, 파싱, 포매팅
앞서 살펴본 시간, 날짜 클래스는 불변이다. withAttribute 메서드를 사용하면 일부 속성이 수정된 상태의 새로운 객체를 반환받을 수 있다.  
get과 with 메서드로 Temporal 객체의 필드값을 읽거나 고칠 수 있으며 Temporal 객체가 지정된 필드를 지원하지 않으면 UnsupportedTemporalTypeException이 발생한다.

* 절대적인 방식으로 LocalDate의 속성 바꾸기
> ~~~java
> LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
> LocalDate date2 = date1.withYear(2011); // 2011-09-21
> LocalDate date3 = date2.withDayOfMonth(25); // 2011-09-25
> LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2); // 2011-02-25
> ~~~
> get과 with메서드로 Temporal객체의 필드값을 읽거나 고칠 수 있다. 

* 상대적인 방식으로 LocalDate의 속성 바꾸기
> ~~~java
> LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
>LocalDate date2 = date1.plusWeek(1); // 2017-09-28
>LocalDate date3 = date2.minusYear(6); // 2011-09-28
>LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS); // 2012-03-28
> ~~~
> get과 with메서드와 비슷한 puli, minus메서드를 사용했다
 
### TemporalAdjusters 사용하기
Java는 다음주 일요일, 돌아오는 평일등 복잡한 날짜 조정기능을 지원한다.  
날짜와 시간 API는 다양한 상황에서 사용할 수 있도록 TemporalAdjuster를 제공한다.
~~~java
LocalDate date1 = LocalDate.of(2014, 3, 18);
LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
LocalDate date3 = date2.with(lastDayOfMonth())
~~~
***TemporalAdjusters 의 팩토리 메소드***  

|메서드|설명|
|---|---|
|dayOfWeekInMonth |서수 요일에 해당하는 날짜를 반환|
|firstDayOfMonth | 현재 달의 첫 번쨰 날짜를 반환
|firstDayOfNextMonth | 다음 날의 첫 번째 날짜를 반환
|firstDayOfNextYear | 내년의 첫 번째 날짜를 반환
|firstDayOfYear | 올해의 첫 번째 날짜를 반환
|firstInMonth | 현재 달의 첫 번째 요일에 해당하는 날짜를 반환
|lastDayOfMonth | 현재달의 마지막 날짜를 반환
|lastDayOfNextMonth| 다음 달의 마지막 날짜를 반환
|lastDayOfNextYear |내년의 마지막 날짜를 반환
|lastDayOfYear | 올해의 마지막 날짜를 반환
|lastInMonth | 현재 달의 마지막 요일에 해당하는 날짜 반환
|next previous |현재 달에서 현재 날짜 이후로 지정한 요일이 처음으로 나타나는 날짜를 반환
|nextOrSame |현재 날짜 이후로 지정한 요일이 처음 / 이전으로 나타나는 날짜를 반환
|previousOrSame | TemporalAdjuster를 반환함(현재날짜포함)

### 날짜와 시간 객체 출력과 파싱
날짜와 시간 관련 작업에서 포매팅과 파싱은 서로 떨어질수 없는 관계이다.  
DateTimeFormmatter 클래스는 BASIC_ISO_DATE와 ISO_LOCAL_DATE등의 상수를 미리 정의하고 있다.
* DateTimeFormmatter를 활용하여 날짜나 시간을 특정 형식의 문자열로 만들 수 있다.
  * ~~~java
      LocalDate date = LocalDate.of(2014, 3, 18);
      date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20140318
      date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2014-03-18
      ~~~
* 반대로 날짜나 시간을 표현하는 문자열을 파싱하여 날짜 객체를 다시 만들 수 있다.
  * ~~~java
        LocalDate parse = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate parse2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
    ~~~

DateTimeFormmatter는 기존 java.uil.DateFormat클래스와 달리 스레드에서 안전하게 사용할 수 있다.  
또한 특정 패턴으로 포매터를 만들 수 있는 정적 팩토리 메서드도 존재한다.
> > **예제코드** : <a href="https://github.com/day0ung/ModernJavaInAction/blob/main/java_code/modern_java/src/chapter12/SourceCode121.java">SourceCode121.dateTimeFormatterExample()</a>

## 12.3 다양한 시간대와 캘린더 활용 방법
새로운 날짜와 시간 API의 편리함 중 하나는 시간대를 간단하게 처리할 수 있다는 점이다.
기존 TimeZone을 대체할 수 있는 ZoneId 클래스(불변클래스)가 새롭게 등장했다.
새로운 클래스를 이용하면 서머타임같은 복잡한 사항이 자동으로 처리된다.
#### 시간대 이용하기
* 표준 시간이 같은 지역을 묶어 시간대 규칙 집합을 정의한다.
* ZoneRules 클래스에는 약 40개 정도의 시간대가 있다.
* ZoneId의 getRules()를 이용해 해당 시간대의 규정을 획득할 수 있다
* 지역 ID로 특정 ZoneId를 구분한다.
  * <code>ZoneId romeZone = ZoneId.of("Europe/Rome");</code>
* 지역 Id는 지역/도시형식으로 이루어지며 IANA Time Zone Database에서 제공하는 지역 집합 정보를 사용한다.
  * <code>ZoneId zoneId = TimeZone.getDefault().toZoneId();
    </code>
  
#### UTC/Greenwich 기준의 고정 오프셋
때로는 UTC(협정 시계시) / GMT (그리니치 표준시) 를 기준으로 시간대를 표현하기도 한다.  
<code> ZoneOffset.of("-5:00"); // 현재 타임존 보다 5시간 느린 곳 정의</code>

