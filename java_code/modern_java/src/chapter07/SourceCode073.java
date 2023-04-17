package src.chapter07;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SourceCode073 {
    public static void main(String[] args) {
        final String SENTENCE = "모던자바인 액션 스트림 Splitarter";
        System.out.println("Found " + SourceCode073.countWordsIteratively(SENTENCE));

    }
    public static int countWordsIteratively(String s){
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()){ // 문자열의 모든 문자를 하나씩 탐색한다
            if(Character.isWhitespace(c)){
                lastSpace = true;
            } else{
                if(lastSpace) counter ++; // 문자를 하나씩 탐색하다 공백 문자를 만나면 지금까지 탐색한 문자를 단어로 간주하여 (공백제외)단어수를 증가시킨다
                lastSpace = false;
            }
        }
        return counter;
    }
}
