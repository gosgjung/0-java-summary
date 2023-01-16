package io.study.modernjavainaction.ch11.ch11_2_optional_simple_example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Optional_BasicExample {

	class Employee{
		private String name;
		private Optional<Department> dept;

		public Employee(String name){
			this.name = name;
		}

		public Employee(String name, Optional<Department> dept){
			this.name = name;
			this.dept = dept;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Optional<Department> getDept() {
			return dept;
		}

		public void setDept(
			Optional<Department> dept) {
			this.dept = dept;
		}

		@Override
		public String toString() {
			return "Employee{" +
				"name='" + name + '\'' +
				", dept=" + dept +
				'}';
		}
	}

	class Department{
		private String name;
		private Optional<Sales> sales;

		public Department(String name, Optional<Sales> sales){
			this.name = name;
			this.sales = sales;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Optional<Sales> getSales() {
			return sales;
		}

		public void setSales(
			Optional<Sales> sales) {
			this.sales = sales;
		}

		@Override
		public String toString() {
			return "Department{" +
				"name='" + name + '\'' +
				", sales=" + sales +
				'}';
		}
	}

	class Sales{
		private Double income;

		public Sales(Double income){
			this.income = income;
		}

		public Double getIncome() {
			return income;
		}

		public void setIncome(Double income) {
			this.income = income;
		}

		@Override
		public String toString() {
			return "Sales{" +
				"income=" + income +
				'}';
		}
	}

	@Test
	@DisplayName("Optional 객체 만들기")
	void testMakeOptionalInstance(){
		// 빈 Optional
		Optional<Employee> optEmptyEmployee = Optional.empty();
		System.out.println("optEmployeeEmployee = " + optEmptyEmployee);

		// null 이 아닌 값으로 Optional 만들기
		Employee e1 = new Employee("소방관");
		Optional<Employee> optEmployee = Optional.of(e1);
		System.out.println("optEmployee = " + optEmployee);

		// null 값으로 Optional 만들기
		// null 값이 존재할 수 밖에 없고, 이것을 Optional.empty() 가 아닌 변수 값에 의해 결정되도록 하고 싶은 경우가 있다.
		// 이렇게 하면 Optional.empty 가 할당되게 된다.
		Employee e2 = null;
		Optional<Employee> optNullEmployee = Optional.ofNullable(e2);
		System.out.println("optNullEmployee = " + optNullEmployee);
	}

	@Test
	@DisplayName("Optional 로 map 을 사용해보기")
	void testOptionalMap1(){
		Employee e1 = new Employee("경찰관1");
		Employee e2 = null;

		Optional<Employee> optE1 = Optional.ofNullable(e1);
		Optional<String> e1Name = optE1.map(Employee::getName);
		System.out.println("e1Name = " + e1Name);

		Optional<Employee> optE2 = Optional.ofNullable(e2);
		Optional<String> e2Name = optE2.map(Employee::getName);
		System.out.println("e2Name = " + e2Name);
	}

	@Test
	@DisplayName("Optional 과 flatMap 을 함께 사용해보기 #1 아래 코드는 컴파일에러")
	void testOptionalFlatMap1(){
		Employee e1 = new Employee("소방관");
		Optional<Employee> employee = Optional.of(e1);

//		employee.map(Employee::getDept)
//			.map(Department::getName)
//			.map(Sales::getIncome);

		// employee.map(Employee::getDept) 의 결과는 Optional<Optional<Department>>  이다.
		Optional<Optional<Department>> department = employee.map(Employee::getDept);
		System.out.println(department);
	}

	@Test
	@DisplayName("Optional 과 flatMap 을 함께 사용해보기 #2 :: flatMap 예제")
	void testOptionalFlatMap2(){
		Sales sales = new Sales(1000D);
		Optional<Sales> optSales = Optional.ofNullable(sales);
		Optional<Department> optDepartment = Optional.ofNullable(new Department("동작소방서", optSales));

		Employee e1 = new Employee("소방관", optDepartment);
		Optional<Employee> optE1 = Optional.of(e1);

		Double optionalIncome = optE1.flatMap(Employee::getDept)
			.flatMap(Department::getSales)
			.map(Sales::getIncome)
			.orElse(0.00D);

		System.out.println("optional income = " + optionalIncome);
	}

	@Test
	@DisplayName("ch11_3_4_Optional의_stream_메서드_사용해보기")
	void ch11_3_4_Optional의_stream_메서드_사용해보기(){
		Department d1 = new Department("광명소방서", Optional.of(new Sales(10000D)));
		Department d2 = new Department("동작소방서", Optional.of(new Sales(20000D)));
		Department d3 = new Department("강남소방서", Optional.of(new Sales(30000D)));

		Employee e1 = new Employee("소방관1", Optional.of(d1));
		Employee e2 = new Employee("소방관2", Optional.of(d2));
		Employee e3 = new Employee("소방관3", Optional.of(d3));

		List<Employee> employees = Arrays.asList(e1, e2, e3);

		Set<Double> data = employees.stream()
			.map(Employee::getDept)
			.map(optDepartment -> optDepartment.flatMap(Department::getSales))
			.map(optSales -> optSales.map(Sales::getIncome))
			.flatMap(Optional::stream)
			.collect(Collectors.toSet());

		System.out.println(data);
	}

	@Test
	@DisplayName("ch11_3_5_디폴트액션과_Optional_언랩")
	void ch11_3_5_디폴트액션과_Optional_언랩(){

	}
}
