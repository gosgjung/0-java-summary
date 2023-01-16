package io.study.modernjavainaction.ch5.ch5_5_reducing;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReducingAPI_Summary {

	@Test
	@DisplayName("요소의_합_구하기1_일반적인_방식")
	void 요소의_합_구하기1_일반적인_방식(){
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		int sum = 0;
		for(Integer n : numbers){
			sum = sum + n;
		}

		System.out.println("sum = " + sum);
		Assertions.assertThat(sum).isEqualTo(55);
	}

	@Test
	@DisplayName("요소의_합_구하기1_리듀싱API_활용")
	void 요소의_합_구하기1_리듀싱API_활용(){
		List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

		Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
		System.out.println("sum = " + sum);
		Assertions.assertThat(sum).isEqualTo(55);
	}

	@Test
	@DisplayName("요소의_합_구하기1_리듀싱API_활용_초기값이_없을경우_Optional을_반환한다")
	void 요소의_합_구하기1_리듀싱API_활용_초기값이_없을경우_Optional을_반환한다(){
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		Optional<Integer> sum = numbers.stream().reduce((a, b) -> a + b);
		System.out.println("sum = " + sum);
		Assertions.assertThat(sum).isEqualTo(Optional.of(55));
	}

	@Test
	@DisplayName("리듀싱을_활용한_최댓값과_최솟값구하기_1__최솟값구하기")
	void 리듀싱을_활용한_최댓값과_최솟값구하기_1__최솟값구하기(){
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		Optional<Integer> min1 = numbers.stream().reduce(Integer::min);
		System.out.println("min1 = " + min1);
		Assertions.assertThat(min1).isEqualTo(Optional.of(1));

		// 또는 아래와 같이 변경 가능하다.
		Optional<Integer> min2 = numbers.stream().reduce((x, y) -> x > y ? y : x);
		System.out.println("min2 = " + min2);
		Assertions.assertThat(min2).isEqualTo(Optional.of(1));
	}

	@Test
	@DisplayName("리듀싱을_활용한_최댓값과_최솟값구하기_2__최댓값구하기")
	void 리듀싱을_활용한_최댓값과_최솟값구하기_2__최댓값구하기(){
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		Optional<Integer> max1 = numbers.stream().reduce(Integer::max);
		System.out.println("max1 = " + max1);
		Assertions.assertThat(max1).isEqualTo(Optional.of(10));

		Optional<Integer> max2 = numbers.stream().reduce((x,y)->x>y ? x : y);
		System.out.println("max2 = " + max2);
		Assertions.assertThat(max2).isEqualTo(Optional.of(10));
	}

	class Employee{
		private Long id;
		private String name;
		private Double salary;

		Employee(Long id, String name, Double salary){
			this.id = id;
			this.name = name;
			this.salary = salary;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public Double getSalary() {
			return salary;
		}
	}

	@Test
	@DisplayName("맵_리듀스패턴_사용해보기_1__직원의_수_계산해보기")
	void 맵_리듀스패턴_사용해보기_1__직원의_수_계산하기(){
		Employee e1 = new Employee(1L, "소방관#1", 1000D);
		Employee e2 = new Employee(2L, "소방관#2", 2000D);
		Employee e3 = new Employee(3L, "소방관#3", 3000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3);

		Integer employeeCnt = employees.stream().map(d -> 1).reduce(0, (a, b) -> a + b);
		Assertions.assertThat(employeeCnt).isEqualTo(3);
	}

	@Test
	@DisplayName("맵_리듀스패턴_사용해보기_2_연봉의_총합_계산해보기")
	void 맵_리듀스패텈_사용해보기_2_연봉의_총합_계산해보기(){
		Employee e1 = new Employee(1L, "소방관#1", 1000D);
		Employee e2 = new Employee(2L, "소방관#2", 2000D);
		Employee e3 = new Employee(3L, "소방관#3", 3000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3);

		Double salarySum = employees.stream().map(d -> d.getSalary()).reduce(0D, (a, b) -> a + b);
		System.out.println(salarySum);

		Assertions.assertThat(salarySum).isEqualTo(6000D);
	}

	@Test
	@DisplayName("단순_카운트는_스트림API의_count_함수로도_구하는_것이_가능하다")
	void 단순_카운트는_스트림API의_count_함수로도_구하는_것이_가능하다(){
		Employee e1 = new Employee(1L, "소방관#1", 1000D);
		Employee e2 = new Employee(2L, "소방관#2", 2000D);
		Employee e3 = new Employee(3L, "소방관#3", 3000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3);

		long count = employees.stream().count();
		System.out.println(count);

		Assertions.assertThat(count).isEqualTo(3);
	}

}
