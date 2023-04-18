package chapter07;

import java.util.Calendar;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class SourceCode074 {
    public static void main(String[] args) {
    final String SENTENCE = "모던자바인 액션 스트림 Splitarter";
        //우선 String을 스트림으로 변환해야한다. 안타깝게도 Stream은 int,long,double 기본형만 제공하므로
        //Stream<Character>를 사용해야한다.
        //함수형으로 단어 수를 세는 메서드 재구현
        Stream<Character> stream = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);

        System.out.println("Found " + WordCounter.countWords(stream));


        //WordCounter 병렬로 수행하기 틀린답
//        System.out.println("Found " + WordCounter.countWords(stream.parallel()));
        //Found 20 으로 원하는 결과가 나오지 않는다.
        // 이유는? 순차 스트림을 병렬 스트림으로 바꿀때 스트림 분할 위치에 따라 잘못된 결과가 나올 수 있다.
        // 해결하려면 문자열을 임의의 위치에서 분할하지 말고 단어가 끝나는 위치에서만 분할하는 방봅으로 이문제를 해결할 수있다.
        // 해결방법은 SourceCode075에서..

        ////WordCounter 병렬로 수행하기 정답!
        Spliterator<Character> spliterator = new src.chapter07.SourceCode075(SENTENCE);
        Stream<Character> stream1 = StreamSupport.stream(spliterator, true);
        System.out.println("Found spliterator " + WordCounter.countWords(stream1));
    }

    // 스트림에 리듀싱 연산을 실행하면서 단어 수를 계산할수 있다.
    // 이때 필요한 것은 지금까지 발견한 단어 수를 계산하는 int 변수와, 마지막 문자가 공백이었는지를 기억하는 boolean 변수가 필요하다
    // 자바에는 튜플(래퍼객체 없이 다형 요소의 정렬 리스트를 표한할수 있는 구조체)가 없으므로 이들상태를 캡슐화하는 새로운 클래스를 만들어야한다.

    static class WordCounter{
        private final int counter;
        private final boolean lastSpace;

        public WordCounter(int counter, boolean lastSpace){
            this.counter = counter;
            this.lastSpace = lastSpace;
        }

        //반복 알고리즘 처럼 문자열의 문자를 하나씩 탐색
        public WordCounter accumalate(Character c){
            if(Character.isWhitespace(c)){
                return lastSpace ?
                        this :
                        new WordCounter(counter, true);
            } else{
                //문자를 하나씩 탐색하다 공배문자를 만나면 지금까지 탐색한 문자를 단어로 간주하여 (공백제외) 단어수 증가
                return lastSpace ?
                        new WordCounter(counter+1, false) :
                        this;
            }
        }

        public WordCounter combine (WordCounter wordCounter){
            return new WordCounter(counter + wordCounter.counter, //두 WordCounter의 counter 값을 더함
                    wordCounter.lastSpace); //counter 값만 더할 것이므로 마지막 공백은 신경 X
        }

        public int getCounter(){
            return counter;
        }

        //문자 스트림의 리듀싱 연산을 지관적으로 구현
        private static int countWords(Stream<Character> stream) {
            WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                                                        WordCounter::accumalate,
                                                        WordCounter::combine);
            return wordCounter.getCounter();
        }

    }

}
