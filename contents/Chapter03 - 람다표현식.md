# 람다 표현식
## 3.1 람다란 무엇인가?
**람다표현식**은 메서드로 전달할수 있는 익명함수를 단순화한것이다
 * 익명 : 보통의 메서드와 달리 이름이 없으므로 익명이라 표현한다. 구형해야 할 코드에 대한 걱정거리가 줄어든다
 * 함수 : 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다. 하지만 메서드처럼 파라미터 리스트, 바디, 반환 형식, 가능한 예외 리스트를 포함한다.
 * 전달 : 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
 * 간결성 : 익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.

 예시)
 ~~~java
 //기존코드
 Comparator<Apple> byWeight = new Comparator<Apple>{
    public int compare(Apple a1, Apple a2){
        return a1.getWeight().comareTo(a2.getWeight());
    }
 }

 //람다코드
 Compare<Apple> byWeight = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
 ~~~

 