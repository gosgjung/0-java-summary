package io.study.modernjavainaction.ch3.ch3_4_built_in_functional_interface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Function_T_And_R {

	/**
	 * Function<T,R> 은 타입 T를 파라미터로 받아서 타입 R을 리턴한다.. 라고 읽으면 된다.
	 * 문자열을 input 으로 받아서 (문자열 타입 - Type(T))
	 * 문자열의 길이(Integer)를 Return(R)
	 */
	@Test
	@DisplayName("Function<T,R> 예제 #1")
	void testFunctionTAndR_1(){
		Function<String, Integer> lengthFunc = (t)->{
			return t.length();
		};

		Integer length = lengthFunc.apply("hello~");
		Assertions.assertThat(length).isEqualTo(6);
		System.out.println(length);
	}

	/**
	 * 인풋 타입 T 를 나열한 컬렉션인 input 을 받아서
	 * 리턴 타입 R과 인풋 타입 T에 대한 람다를 받아서 연산을 수행하도록 하는 함수.
	 * 리스트의 각 요소 T에 대해 mapperFn을 적용한 값 R을 리스트로 만든 값을 반환.
	 * @param input
	 * @param mapperFn
	 * @param <T>
	 * @param <R>
	 * @return
	 */
	public <T,R> List<R> mappingByFunction(List<T> input, Function<T, R> mapperFn){
		List<R> list = new ArrayList<>();
		for(T d : input){
			list.add(mapperFn.apply(d));
		}
		return list;
	}

	/**
	 * Function<T,R> 을 메서드의 파라미터로 전달해보기
	 */
	@Test
	@DisplayName("Function<T,R> 예제 #2")
	void testFucntionTAnR_2(){
		List<String> words = Arrays.asList("Apple", "Banana", "Carrot", "Dragon", "Eureka");

		List<Integer> integers = mappingByFunction(words, t -> {
			return t.length();
		});

		System.out.println(integers);
	}

	/**
	 * 언박싱 예제
	 */
	@Test
	@DisplayName("언박싱")
	void testFunctionTAndR_3(){
		List<String> words = Arrays.asList("Apple", "Banana", "Carrot", "Dragon", "Eureka");

		List<Integer> integers = mappingByFunction(words, t -> {
			return t.length();
		});

		System.out.println(integers);

		int sum = 0;
		for (int i =0; i<integers.size(); i++){
			sum = sum + integers.get(i);
		}
		System.out.println(sum);
	}

	/**
	 * 기본형 특화 함수형 인터페이스 #1 > IntPredicate 예제
	 */
	@Test
	@DisplayName("IntPredicate 예제")
	void testIntPredicate_1(){
		IntPredicate evenOddPredicate = (t)->{
			boolean oddOrNotFlag = (t%2) == 1;
			return oddOrNotFlag;
		};

		boolean oddOrNot_ten = evenOddPredicate.test(10);
		boolean oddOrNot_eleven = evenOddPredicate.test(11);

		System.out.println("is " + 10 + " odd number ? " + oddOrNot_ten);
		System.out.println("is " + 11 + " odd number ? " + oddOrNot_eleven);
	}

	/**
	 * 람다와 함수형 인터페이스 예제 > 1) 불리언 표현
	 */
	@Test
	@DisplayName("람다와 함수형 인터페이스 예제 > 1) 불리언 표현")
	void testLambdaAndFucntionalInterface_1(){
		List<String> employees = new ArrayList<>();
		employees.add("Jobs");
		employees.add("이재용");
		employees.add("머스크");

		Predicate<List<String>> pre = (List<String> list) -> list.isEmpty();
		boolean test = pre.test(employees);
		System.out.println(" 리스트 'employees' 가 비어있나요 ??? " + test);
	}

	class Apple {
		private int weight;

		public Apple(int weight){
			this.weight = weight;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Apple{" +
				"weight=" + weight +
				'}';
		}
	}

	/**
	 * 람다와 함수형 인터페이스 예제 > 2) 객체 생성
	 */
	@Test
	@DisplayName("람다와 함수형 인터페이스 예제 > 2) 객체 생성")
	void testLambdaAndFucntionalInterface_2(){
		int weight = 1000;
		Supplier<Apple> appleSupplier = () -> new Apple(weight);

		Apple apple1 = appleSupplier.get();
		System.out.println("생성된 객체 apple1 :: " + apple1.toString());
	}

	/**
	 * 람다와 함수형 인터페이스 예제 > 3) 객체에서 소비
	 */
	@Test
	@DisplayName("람다와 함수형 인터페이스 예제 > 3) 객체에서 소비")
	void testLambdaAndFucntionalInterface_3(){
		Consumer<Apple> appleConsumer = (Apple a) -> {
			System.out.println(a.toString());
		};

		appleConsumer.accept(new Apple(100));
	}

	/**
	 * 람다와 함수형 인터페이스 예제 > 4) 객체에서 선택/추출
	 */
	@Test
	@DisplayName("람다와 함수형 인터페이스 예제 > 4) 객체에서 선택/추출 ")
	void testLambdaAndFucntionalInterface_4(){
		// 1) Function 사용해보기
		Function<String, Integer> lengthFunc = string -> {
			return string.length();
		};
		Integer appleLength = lengthFunc.apply("Apple");
		System.out.println("appleLength ::: " + appleLength);

		// 2) ToIntFunction 사용해보기
		ToIntFunction<String> lengthToIntFunc = string -> {
			return string.length();
		};
		int applyAsInt1 = lengthToIntFunc.applyAsInt("Banana, Banana");
		System.out.println("applyAsInt1 ::: " + applyAsInt1);

		// 3) 메서드 레퍼런스 형식으로 표현해보기
		ToIntFunction<String> applyAsInt2 = String::length;
		System.out.println("applyAsInt2 ::: " + applyAsInt2);
	}
}
