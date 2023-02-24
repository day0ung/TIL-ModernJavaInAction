# 병렬데이터 처리와 성능
자바 7이 등장하기 전에는 자바에서 병렬처리를 할려면 많은 노력을 해야했다.
1. 데이터를 서브파트로 분할
2. 분할된 서브파트를 각각의 스레드로 할당
3. 할당 후 의도치 않은 레이스 컨디션(경쟁 상태) 가 발생하지 않도록적절한 동기화 추가
4. 마지막으로 부분 결과를 합침
 
자바 7은 쉽게 병렬화를 수행하면서 에러를 최소화할 수 있도록 포크/조인 프레임워크(fork/join framework) 기능을 제공한다.
자바 8이후부터 나온 stream 파이프라인으로 병렬처리를 하는 방법과 내부적으로 일어나는 과정, 그 과정에서 발생하는 병렬 스트림의 성능에 대해 알아본다.


* [병렬 스트림](#71-병렬-스트림)
* [포크/조인 프레임워크](#72-포크조인-프레임워크)
* [Spliterator 인터페이스](#73-spliterator-인터페이스)


## 7.1 병렬 스트림
📌 병렬 스트림이란, 각각의 **스레드**에서 처리할 수 있도록 스트림 요소를 여러 청크로 분할한 스트림이다.  
따라서 병렬 스트림을 이용하면 모든 멀티코어 프로세서가 각각의 청크를 처리하도록 할당할 수 있다.

컬렉션에서 parallelStream 을 호출하면 병렬 스트림이 생성된다. 병렬 스트림이란 각각의 스레드에서 처리할 수 있도록 스트림 요소를 여러 chunk로 분할한 스트림이다.  

* 숫자 n을 인수로 받아서 1부터 n까지의 모든 숫자의 합계를 반환하는 메서드를 구현해보자.
  * ~~~java
      //스트림 사용
      public long sequentialSum() {
        return Stream.iterate(1L, i -> i + 1)
        .limit(N)
        .reduce(0L, Long::sum);
      }
  
      // 전통적인 for-loop
      public long iterativeSum() {
        long result = 0;
        for(long i = 1L; i<N; i++) {
          result += i;
        }
        return result;
      }
    ~~~
* 위의 연산은 N이 커진다면 부하가 커질 것이므로 병렬로 처리하는 것이 좋다. 이제 병렬 스트림을 사용해보자
  * ~~~java
    public long parallelSum() {
       return Stream.iterate(1L, i -> i + 1).limit(N)
       .parallel() // 스트림을 병렬 스트림으로 변환
       .reduce(0L, Long::sum);
    }
    ~~~
  * **parallel()** 메서드를 쓰면 스트림이 여러 chunk 로 분할된다. 이렇게 분할된 chunk를 병렬로 수행한 뒤에, 리듀싱 연산으로 합쳐서 전체 스트림의 리듀싱 결과를 도출한다.

> 이렇게 만들어진 스레드는 어디서 생성되며 몇 개나 생성될까? 병렬 스트림은 내부적으로 ForkJoinPool 을 사용한다.  
ForkJoinPool은 프로세서 수, 즉 Runtime.getRuntime().availableProcessors() 가 반환하는 값에 상응하는 스레드를 갖는다
> ~~~java
> // 전역설정코드!! 시스템의 병렬 스레드 수를 지정할 수 있다.
> System.setProperty("java.util.concurrent.ForJoinPool.common.parallelism", "12");
> ~~~

### 스트림 성능 측정
병렬화를 이용하면 순차나 반복 형식에 비해 성능이 더 좋아질 것이라 추측했다. 하지만 가장 좋은 방법은 직접 측정하는 것이다. 자바 마이크로벤치마크 하니스(Java Microbenchmark Harness) JHM 라이브러리를 이용해 벤치마크를 구현해본다. 

JMH를 이용하면 간단하고, 어노테이션 기반 방식을 지원하며, 안정적으로 자바 프로그램이나 자바 가상 머신(JVM)을 대상으로 하는 다른 언어용 벤치마크를 구현할 수 있다.

***Maven 설정***
~~~java
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-core</artifactId>
	<version>1.17.4</version>
</dependency>
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-generator-annprocess</artifactId>
	<version>1.17.4</version>
</dependency>

<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-shade-plugin</artifactId>
			<executions>
				<execution>
					<phase>package</phase>
					<goals><goal>shade</goal></goals>
					<configuration>
						<finalName>benchmarks</finalName>
						<transformers>
							<transformer implementation=
									"org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
								<mainClass>org.openjdk.jmh.Main</mainClass>
							</transformer>
						</transformers>
					</configuration>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
~~~
***n개의 숫자를 더하는 함수의 성능 측정***
~~~java
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
@State(Scope.Benchmark)
public class MyBenchmark {
    private static final long N = 10_000_000L;

    @Benchmark  // 벤치마크 대상 메서드
    public long benchmark() {
			// n개의 숫자를 더하는 로직 구현
    }

    @TearDown(Level.Invocation) // 매 번 벤치마크를 실행한 다음에는 가비지 컬렉터 동작 시도
    public void tearDown() {
        System.gc();
    }
}
~~~
* 
## 7.2 포크/조인 프레임워크
## 7.3 Spliterator 인터페이스