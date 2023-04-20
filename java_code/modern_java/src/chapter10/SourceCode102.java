package chapter10;

import chapter03.SourceCode033;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceCode102 {
    private static final String FILE = SourceCode102.class.getResource("./error.txt").getFile();
    //로그 파일을 읽어 "ERROR"라는 단어로 시작하는 파일의 첫 40행을 수집하는 작업 수행
    public static void main(String[] args) throws IOException {
        errorRead();
        errorReadToStream();
    }

    private static void errorReadToStream() throws IOException{
        List<String> errors = Files.lines(Paths.get(FILE))
                .filter(line -> line.startsWith("ERROR"))
                .limit(40)
                .collect(Collectors.toList());
        System.out.println("stream : " + errors);
    }

    public static void errorRead() throws IOException {
        List<String> error = new ArrayList<>();
        int errorCount = 0;
        BufferedReader br = new BufferedReader(new FileReader(FILE));
        String line = br.readLine();
        while(errorCount < 40 && line != null){
            if(line.startsWith("ERROR")){
                error.add(line);
                errorCount ++;
            }
            line = br.readLine();
        }
        System.out.println("basic : "+ error.toString());


    }
}
