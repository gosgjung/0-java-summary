package io.study.modernjavainaction.playground;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

public class ReactivePlay {

	@Test
	@DisplayName("모노_샘플_사용해보기")
	void 모노_샘플_사용해보기(){
		Mono.just("Craig")
			.map(n -> n.toUpperCase())
			.map(cn -> "Hello, " + cn + "!")
			.subscribe(System.out::println);
	}

	@Test
	@DisplayName("객체들을_받아서_Flux_객체_생성하기")
	void 객체들을_받아서_Flux_객체_생성하기(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		fruitFlux.subscribe( fruit -> System.out.println("Here's some fruit :: " + fruit) );
	}

	@Test
	@DisplayName("Assertion_사용해보기_StepVerifier")
	void Assertion_사용해보기_StepVerifier(){
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		StepVerifier.create(fruitFlux)
			.expectNext("Apple")
			.expectNext("Orange")
			.expectNext("Grape")
			.expectNext("Banana")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("String_배열로부터_생성하기")
	void String_배열로부터_생성하기(){
		String [] fruits = new String [] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

		Flux<String> stringFlux = Flux.fromArray(fruits);

		StepVerifier.create(stringFlux)
			.expectNext("Apple")
			.expectNext("Orange")
			.expectNext("Grape")
			.expectNext("Banana")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("Iterable로부터_생성하기")
	void Iterable로부터_생성하기(){
		List<String> fruitList = new ArrayList<>();
		fruitList.add("Apple");
		fruitList.add("Orange");
		fruitList.add("Grape");
		fruitList.add("Banana");
		fruitList.add("Strawberry");

		Flux<String> fruitFlux = Flux.fromIterable(fruitList);

		StepVerifier.create(fruitFlux)
			.expectNext("Apple")
			.expectNext("Orange")
			.expectNext("Grape")
			.expectNext("Banana")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("Stream_으로부터_생성하기")
	void Stream_으로부터_생성하기(){
		Stream<String> dataStream = Stream.of("Apple", "Banana", "Grape", "Cherry", "Strawberry");

		Flux<String> stringFlux = Flux.fromStream(dataStream);

		StepVerifier.create(stringFlux)
			.expectNext("Apple")
			.expectNext("Banana")
			.expectNext("Grape")
			.expectNext("Cherry")
			.expectNext("Strawberry")
			.verifyComplete();
	}

	@Test
	@DisplayName("Flux_데이터_축_생성하기")
	void Flux_데이터_축_생성하기(){
		// 숫자 0 에서 부터 5개의 숫자를 생성해낸다.
		System.out.println("range #1");
		Flux<Integer> range1 = Flux.range(0, 5);
		range1.subscribe(n -> System.out.print(n + "->"));
		System.out.println("||");

		// 숫자 3 에서 부터 5개의 숫자를 생성해낸다.
		System.out.println();
		System.out.println("range #2");
		Flux<Integer> range2 = Flux.range(3,5);
		range2.subscribe(n -> System.out.print(n + "->"));
		System.out.println("||");
	}

	@Test
	@DisplayName("Flux_초_간격으로_실행시켜보기")
	void Flux_초_간격으로_실행시켜보기(){
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1))
			.take(7);

//		intervalFlux.subscribe(interval -> System.out.print(interval + "->"));
//		System.out.println("||");

		StepVerifier.create(intervalFlux)
			.expectNext(0L)
			.expectNext(1L)
			.expectNext(2L)
			.expectNext(3L)
			.expectNext(4L)
			.expectNext(5L)
			.expectNext(6L)
			.verifyComplete();
	}

	@Test
	@DisplayName("여러가지_Flux_타입들_결합하기")
	void 여러가지_Flux_타입들_결합하기(){
		Flux<String> employee = Flux.just("소방관#1", "소방관#2", "소방관#3")
			.delayElements(Duration.ofMillis(500));

		// 방법 1) 바로 만들기
		Flux<String> movieFlux = Flux.just("봄날은 간다", "승리호")
			.delaySubscription(Duration.ofMillis(250))
			.delayElements(Duration.ofMillis(500));

		// 방법 2) 리스트에서 Flux로 변환해보기
//		List<String> movies = Arrays.asList("봄날은 간다", "승리호");
//		Flux<String> movieFlux = Flux.fromIterable(movies)
//			.delaySubscription(Duration.ofMillis(250))
//			.delayElements(Duration.ofMillis(200));

		Flux<String> mergedFlux = employee.mergeWith(movieFlux);

		StepVerifier.create(mergedFlux)
			.expectNext("소방관#1")
			.expectNext("봄날은 간다")
			.expectNext("소방관#2")
			.expectNext("승리호")
			.expectNext("소방관#3")
			.verifyComplete();
	}

	/**
	 * zip :: 하나의 요소씩 번갈아가며 합치기
	 * ex)
	 * 	f1 : ["소방관#1", "소방관#2", "소방관#3"], f2 : ["봄날은 간다.", "승리호"]
	 *
	 * 	Flux.zip(f1, f2);
	 * 	결과) [소방관#1,봄날은 간다.]->[소방관#2,승리호]->||
	 */
	@Test
	@DisplayName("zip_서로다른_Flux들의_값이_완전히_번갈아가며_호출되도록_지정")
	void zip_서로다른_Flux들의_값이_완전히_번갈아가며_호출되도록_지정(){
		Flux<String> employeesFlux = Flux.just("소방관#1", "소방관#2", "소방관#3");
		Flux<String> moviesFlux = Flux.just("봄날은 간다.", "승리호");

		Flux<Tuple2<String, String>> merzippedFlux = Flux.zip(employeesFlux, moviesFlux);

		merzippedFlux.subscribe(element -> System.out.print(element + "->"));
		System.out.println("||");
	}
}
