package io.study.modernjavainaction.ch3.ch3_8_useful_methods_to_combine_lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Comparator_T {

	class Employee {
		private String name;
		private BigDecimal salary;

		public Employee(String name, BigDecimal salary){
			this.name = name;
			this.salary = salary;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public BigDecimal getSalary() {
			return salary;
		}

		public void setSalary(BigDecimal salary) {
			this.salary = salary;
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
	@DisplayName("Comparator 를 어떻게 선언하는 것일까?")
	void testComparing1(){
		Comparator<Employee> c = Comparator.comparing(Employee::getSalary);
	}

	@Test
	@DisplayName("Comparator 를 이용해서 역순으로 정렬해보기")
	void testComparing2(){
		Employee e1 = new Employee("소방관#1", new BigDecimal(100000000));
		Employee e2 = new Employee("소방관#2", new BigDecimal(200000000));
		Employee e3 = new Employee("소방관#3", new BigDecimal(300000000));

		List<Employee> employees = new ArrayList<>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);

		System.out.println("======= Before Sort =======");
		System.out.println(employees.toString());

		Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
		employees.sort(comparator.reversed());
		System.out.println("======= After Sort =======");
		System.out.println(employees.toString());
	}

	@Test
	@DisplayName("Comparator 를 이용해서 연봉 순으로 역순으로 정렬 후 이름 순으로 오름차순")
	void testComparing3(){
		Employee e1 = new Employee("소방관#1", new BigDecimal(100000000));
		Employee e2 = new Employee("소방관#2", new BigDecimal(200000000));
		Employee e3 = new Employee("소방관#3", new BigDecimal(300000000));
		Employee e4 = new Employee("소방관#4", new BigDecimal(300000000));
		Employee e5 = new Employee("소방관#5", new BigDecimal(300000000));
		Employee e6 = new Employee("개발자#1", new BigDecimal(300000000));
		Employee e7 = new Employee("개발자#2", new BigDecimal(300000000));

		List<Employee> employees = new ArrayList<>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		employees.add(e4);
		employees.add(e5);
		employees.add(e6);
		employees.add(e7);

		System.out.println("======= Before Sort =======");
		employees.stream().forEach(System.out::println);

		Comparator<Employee> comparator = Comparator
												.comparing(Employee::getSalary).reversed()
												.thenComparing(Employee::getName);
		employees.sort(comparator);
		System.out.println("======= After Sort =======");
		employees.stream().forEach(System.out::println);
	}
}
