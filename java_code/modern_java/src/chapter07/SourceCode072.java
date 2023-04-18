package chapter07;

import java.util.concurrent.RecursiveTask;

// 포크/조인 프레임워크를 이용해서 병렬 합계 수행
public class SourceCode072 extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 500; // 이 값 이하의 서브태스크는 더이상 분할할수 없다.

    //메인 태스크를 생성할 때 사용할 공개 생성자
    public SourceCode072(long[] numbers) {
        this(0, numbers.length, numbers);
    }

    //메인 태스크의 서브태스크를 재귀적으로 만들 때 사용할 비공개 생성자
    private SourceCode072(int start, int end, long[] numbers) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override //RecursiveTask의 추상메서드 오버라이드
    protected Long compute() {
        int length = end - start; //이 태스크에서 더할 배열의 길이 
        if(length <= THRESHOLD) {
            return computeSequentially();
        }
        SourceCode072 leftTask = //배열의 첫번째 절반을 더하도록 서브태스크를 생성한다
                new SourceCode072(start, start + length / 2, numbers);
        leftTask.fork(); // ForkJoinPool의 다른 스레드로 새로 생성한 태스크를 비동기로 실행한다.

        SourceCode072 rightTask = //배열의 나머지 절반을 더하도록 서브태스크 생성한다
                new SourceCode072(start + length / 2, end, numbers);
        Long rightResult = rightTask.compute(); //두번재 서브태스크를 동기실행한다. 이때 추가로 분할이 일어날 수있다.
        Long leftResult = leftTask.join(); // 첫번재 서브태스크의 결과를 읽거나 아지 결과가 없으면 기다린다

        return leftResult + rightResult; // 두 서브태스크의 결과를 조합한 값이 이 태스크의 결과이다
    }
    
    // 더 분할할 수 없을때 서브태스크의 결과를 계산하는 단순한 알고리즘
    private long computeSequentially() {
        long sum = 0;
        for(int i = start ; i < end ; i++) sum += numbers[i];
        return sum;
    }



}
