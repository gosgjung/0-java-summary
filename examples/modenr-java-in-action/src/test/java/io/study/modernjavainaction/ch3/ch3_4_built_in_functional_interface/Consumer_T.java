package io.study.modernjavainaction.ch3.ch3_4_built_in_functional_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Consumer_T {

	@Test
	@DisplayName("Consumer 예제 #1 > 문자열 길이 출력하기")
	void testConsumer_T_1(){
		Consumer<String> printLengthNewLine = (t) -> {
			System.out.println(t.length());
		};
//		List<String> inputList = Arrays.asList("ABC", "DEF", "GH", "I", "");
		String inputString = "ABC";
		printLengthNewLine.accept(inputString);
	}

	/**
	 * Consumer 인터페이스 타입의 람다를 받아서 동작하는 어셉터
	 * @param list
	 * @param consumer
	 * @param <T>
	 */
	public <T> void accpetor (List<T> list, Consumer<T> consumer){
		List<T> result = new ArrayList<>();
		for (T t: list){
			consumer.accept(t);
		}
	}

	/**
	 * 파라미터로 Consumer 타입의 람다를 받아서 처리해보기
	 */
	@Test
	@DisplayName("Consumer 예제 #2 > 파라미터로 Consumer 받아서 처리해보기")
	void testConsumer_T_2(){
		List<String> list = Arrays.asList("ABC", "DEF", "HI", "J", "");
		accpetor(list, t -> {
			System.out.println(t.length());
		});
	}
}
