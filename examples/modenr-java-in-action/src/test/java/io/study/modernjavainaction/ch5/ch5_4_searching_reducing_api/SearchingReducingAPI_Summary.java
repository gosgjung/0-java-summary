package io.study.modernjavainaction.ch5.ch5_4_searching_reducing_api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchingReducingAPI_Summary {
	public static final double HIGH_SALARY_LIMIT 	= 5500D;
	public static final double BASIC_SALARY_LIMIT 	= 1000D;

	class Employee {
		private String name;
		private Double salary;

		public Employee(String name, Double salary){
			this.name = name;
			this.salary = salary;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getSalary() {
			return salary;
		}

		public void setSalary(Double salary) {
			this.salary = salary;
		}

		public boolean isHighSalaryEmployee() {
			return this.salary >= HIGH_SALARY_LIMIT;
		}

		public boolean isBasicSalarySatisfied(){
			return this.salary >= BASIC_SALARY_LIMIT;
		}

		@Override
		public String toString() {
			return "Employee{" +
				"name='" + name + '\'' +
				", salary=" + salary +
				'}';
		}
	}

	@Test
	@DisplayName("anyMatch")
	void testAnyMatch(){
		Employee e1 = new Employee("개발자#1", 3000D);
		Employee e2 = new Employee("개발자#2", 4300D);
		Employee e3 = new Employee("개발자#3", 5000D);
		Employee e4 = new Employee("개발자#4", 5400D);
		Employee e5 = new Employee("개발자#5", 5500D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);
		if(employees.stream().anyMatch(Employee::isHighSalaryEmployee)){
			System.out.println("흠... 연봉 5500 이상인 분이 계시긴 하군요...");
		}

		System.out.println();
		employees.stream().forEach(System.out::println);

		System.out.println();
	}

	@Test
	@DisplayName("allMatch")
	void testAllMatch(){
		Employee e1 = new Employee("개발자#1", 3000D);
		Employee e2 = new Employee("개발자#2", 4300D);
		Employee e3 = new Employee("개발자#3", 5000D);
		Employee e4 = new Employee("개발자#4", 5400D);
		Employee e5 = new Employee("개발자#5", 5500D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		if(!employees.stream().allMatch(Employee::isHighSalaryEmployee)){
			System.out.println("흠... 직원 모두가 연봉 5500 이지는 않네요");
		}


		if(employees.stream().allMatch(employee -> employee.getSalary() > BASIC_SALARY_LIMIT)){
			System.out.println("직원 모두가 기본연봉 하한은 충족되긴 하네요..");
		}

		System.out.println();
		employees.stream().forEach(System.out::println);
		System.out.println();
	}

	@Test
	@DisplayName("NoneMatch")
	void testNoneMatch(){
		Employee e1 = new Employee("개발자#1", 3000D);
		Employee e2 = new Employee("개발자#2", 4300D);
		Employee e3 = new Employee("개발자#3", 5000D);
		Employee e4 = new Employee("개발자#4", 5400D);
		Employee e5 = new Employee("개발자#5", 5500D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		if(!employees.stream().allMatch(employee -> employee.getSalary() <= BASIC_SALARY_LIMIT)){
			System.out.println(BASIC_SALARY_LIMIT + " 이하의 연봉을 받는 분이 없군요. 다행입니다...");
		}

		System.out.println();
		employees.stream().forEach(System.out::println);
		System.out.println();
	}

	@Test
	@DisplayName("findAny")
	void testFindAny(){
		Employee e1 = new Employee("개발자#1", 3000D);
		Employee e2 = new Employee("개발자#2", 4300D);
		Employee e3 = new Employee("개발자#3", 5000D);
		Employee e4 = new Employee("개발자#4", 5400D);
		Employee e5 = new Employee("개발자#5", 5500D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		Optional<Employee> any = employees.stream()
			.filter(Employee::isBasicSalarySatisfied)
			.findAny();

		System.out.println(any);
	}

	@Test
	@DisplayName("findFirst() #1")
	void testFindFirst1(){
		List<Integer> someNumbers = Arrays.asList(1,2,3,4,5);
		Optional<Integer> firstSquareDivisibleByThree =
			someNumbers.stream()
				.map(n -> n*n)
				.filter(n -> n%3 == 0)
				.findFirst();

		System.out.println(firstSquareDivisibleByThree);
	}

	@Test
	@DisplayName("findFirst() #2")
	void testFindFirst2(){
		Employee e1 = new Employee("개발자#1", 3000D);
		Employee e2 = new Employee("개발자#2", 4300D);
		Employee e3 = new Employee("개발자#3", 5000D);
		Employee e4 = new Employee("개발자#4", 5400D);
		Employee e5 = new Employee("개발자#5", 5500D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		Optional<Employee> any = employees.stream()
			.filter(employee -> employee.getSalary() >= 5000D)
			.findFirst();

		System.out.println(any);
	}

	@Test
	@DisplayName("Optional")
	void testOptional(){
		Employee e1 = new Employee("개발자#1", 3000D);
		Employee e2 = new Employee("개발자#2", 4300D);
		Employee e3 = new Employee("개발자#3", 5000D);
		Employee e4 = new Employee("개발자#4", 5400D);
		Employee e5 = new Employee("개발자#5", 5500D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		employees.stream()
			.filter(employee -> employee.getSalary() >= 5000D)
			.findAny()
			.ifPresent(employee -> System.out.println(employee.getName()));
	}
}
