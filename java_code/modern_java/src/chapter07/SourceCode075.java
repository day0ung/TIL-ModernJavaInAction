package src.chapter07;

import java.util.Spliterator;
import java.util.function.Consumer;

public class SourceCode075 implements Spliterator<Character> {
    // 단어 끝에서 문자열을 분할하는 문자 Spliterator가 필요하다
    // 문자 Spliterator를 구현한 다음에 병렬스트림으로 전달하는 코드
    private final String string;
    private int currentChar = 0;

    public SourceCode075(String string) {
        this.string = string;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(string.charAt(currentChar++)); //현재 문자를 소비한다
        return currentChar < string.length();
        // 다음 요소가 남아있으면 true를 반환하고, 요소가 더 이상 없으면 false를 반환합니다.
        // 여기서 currentChar은 현재 처리 중인 문자의 인덱스를 나타내며, string.length()는 문자열의 총 길이
        // 따라서 currentChar < string.length()는 아직 처리하지 않은 문자가 남아있는지 여부를 확인하는 조건
        // 반환 값이 true이면 다음 요소로 처리를 진행하고, false이면 스트림 처리를 종료
    }

    @Override
    public Spliterator<Character> trySplit() {
        int currentSize = string.length() - currentChar;
        if(currentSize < 10){
            return null; //파싱할 문자열을 순차 처리할 수 있을 만큼 충빈히 작아졌음을 알리는 null 반환
        }
        for( int splitPos = currentSize /2 + currentChar;
             splitPos < string.length(); splitPos++){
            if(Character.isWhitespace(string.charAt(splitPos))){
                Spliterator<Character> spliterator =
                        new SourceCode075(string.substring(currentChar, splitPos));
                currentChar = splitPos;
                return spliterator;
            }
        }
        return null;
    }

    @Override
    public long estimateSize() {
        return string.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
    // WordCounter 병렬로 수행하기
}
