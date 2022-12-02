package chapter03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SourceCode033 {
    private static final String FILE = SourceCode033.class.getResource("./data.txt").getFile();

    //3.3 실행어라운드 패턴

    public static void main(String[] args) throws IOException {

        System.out.println(processFile());

    }

    public static String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            return br.readLine(); //<-실제 필요한 작업을 하는 행
        }
    }
    //file을 열고 닫으려면 connection이 open/close의 동작은 항상 필요하다.

    
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;

    }
}
