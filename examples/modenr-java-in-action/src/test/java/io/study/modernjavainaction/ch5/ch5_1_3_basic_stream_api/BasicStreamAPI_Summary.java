package io.study.modernjavainaction.ch5.ch5_1_3_basic_stream_api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicStreamAPI_Summary {

	@Test
	void testSplitString(){
		String apple = "Apple";
		String[] split = apple.split("");
		for (String s : split){
			System.out.println("s = " + s);
		}
	}

	class Employee {
		private String name;
		private Department department;
		private Double salary;

		public Employee(String name, Department department, Double salary){
			this.name = name;
			this.department = department;
			this.salary = salary;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Department getDepartment() {
			return department;
		}

		public void setDepartment(
			Department department) {
			this.department = department;
		}

		public Double getSalary() {
			return salary;
		}

		public void setSalary(Double salary) {
			this.salary = salary;
		}

		public boolean isDeveloper(){
			return (this.department.jobType.equals(JobType.DEVELOPER));
		}

		@Override
		public String toString() {
			return "Employee{" +
				"name='" + name + '\'' +
				", department=" + department +
				", salary=" + salary +
				'}';
		}
	}

	class Department{
		private String name;
		private JobType jobType;

		public Department(String name, JobType jobType){
			this.name = name;
			this.jobType = jobType;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public JobType getJobType() {
			return jobType;
		}

		public void setJobType(
			JobType jobType) {
			this.jobType = jobType;
		}

		@Override
		public String toString() {
			return "Department{" +
				"name='" + name + '\'' +
				", jobType=" + jobType +
				'}';
		}
	}

	enum JobType{
		DEVELOPER(1000, "DEVELOPER"){},
		FIREFIGHTER(2000, "FIREFIGHTER"){};

		private int jobCode;
		private String jobNm;

		JobType(int jobCode, String jobNm){
			this.jobCode = jobCode;
			this.jobNm = jobNm;
		}

		public int getJobCode() {
			return jobCode;
		}

		public void setJobCode(int jobCode) {
			this.jobCode = jobCode;
		}

		public String getJobNm() {
			return jobNm;
		}

		public void setJobNm(String jobNm) {
			this.jobNm = jobNm;
		}

		@Override
		public String toString() {
			return "JobType{" +
				"jobCode=" + jobCode +
				", jobNm='" + jobNm + '\'' +
				'}';
		}
	}

	@Test
	@DisplayName("chapter.5.1.1 :: filter 메서드 테스트")
	void testSampleFilterMethod(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3);

		List<Employee> onlyDeveloper = employees.stream()
			.filter(Employee::isDeveloper)
			.collect(Collectors.toList());

		onlyDeveloper.forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.1.2 :: filter, distinct")
	void testSampleFilterDistinctMethod(){
		List<Integer> numbers = Arrays.asList(1,2,3,7,5,2,3);

		numbers.stream()
			.filter(i->i%2 == 1)
			.distinct()
			.forEach(System.out::println);
	}

	@Test
	void testFlatMapAPI_1_intro(){
		List<String> words = new ArrayList<>();
		words.add("apple");
		words.add("banana");
		words.add("cherry");
		words.add("world");
		words.add("mango");

		List<String[]> destructuredStringList = words.stream()
			.map(word -> word.split(""))
			.distinct()
			.collect(Collectors.toList());
	}

	@Test
	void testFlatMapAPI_2_intro(){
		List<String> words = new ArrayList<>();
		words.add("apple");
		words.add("banana");
		words.add("cherry");
		words.add("world");
		words.add("mango");

		List<Stream<String>> destructuredStringList = words.stream()
			.map(word -> word.split(""))
			.map(Arrays::stream)
			.distinct()
			.collect(Collectors.toList());
	}

	@Test
	void testFlatMapAPI_3_usingFlatMap(){
		List<String> words = new ArrayList<>();
		words.add("apple");
		words.add("banana");
		words.add("cherry");
		words.add("world");
		words.add("mango");

		List<String> destructuredStringList = words.stream()
			.map(word -> word.split(""))
			.flatMap(Arrays::stream)
			.distinct()
			.sorted()
			.collect(Collectors.toList());

		destructuredStringList.stream().forEach(s->{
			System.out.print(s + " ");
		});
		System.out.println("");
	}

	@Test
	@DisplayName("chapter.5.2 :: takeWhile, dropWhile(), limit(), skip() 을 사용하지 않을 경우")
	void testChapter5_2_Sample1(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<Employee> filteredList = employees.stream()
			.filter(employee -> {
				return employee.getSalary() < 3000D;
			})
			.collect(Collectors.toList());

		filteredList.forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.2 :: takeWhile()")
	void testChapter5_2_Sample2(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<Employee> filteredList = employees.stream()
			.takeWhile(employee -> employee.getSalary() < 3000D)
			.collect(Collectors.toList());

		filteredList.stream().forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.2 :: dropWhile()")
	void testChapter5_2_Sample3(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<Employee> filteredList = employees.stream()
			.dropWhile(employee -> employee.getSalary() < 3000D)
			.collect(Collectors.toList());

		filteredList.stream().forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.2.2 :: 스트림 축소")
	void testChapter5_2_2_Limit(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<Employee> limitResult = employees.stream()
			.filter(employee -> employee.getSalary() < 5000D)
			.limit(2)
			.collect(Collectors.toList());

		limitResult.stream()
			.forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.2.3 :: 요소 건너뛰기")
	void testChapter5_2_3_Skip(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<Employee> skippedList = employees.stream()
			.filter(employee -> employee.getSalary() < 5000D)
			.skip(2)
			.collect(Collectors.toList());

		skippedList.stream()
			.forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.3 :: 매핑 (map(), flatMap())")
	void testMapFunction_Example1(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<String> employeeNameList = employees.stream()
			.map(Employee::getName)
			.collect(Collectors.toList());

		employeeNameList.stream()
			.forEach(System.out::println);
	}

	@Test
	@DisplayName("chapter.5.3 :: 매핑 (map(), flatMap())")
	void testMapFunction_Example2(){
		Employee e1 = new Employee("소방관", new Department("동작소방서", JobType.FIREFIGHTER), 1000D);
		Employee e2 = new Employee("개발자1", new Department("넷플릭스", JobType.DEVELOPER), 2000D);
		Employee e3 = new Employee("개발자2", new Department("넷플릭스", JobType.DEVELOPER), 3000D);
		Employee e4 = new Employee("개발자3", new Department("넷플릭스", JobType.DEVELOPER), 4000D);
		Employee e5 = new Employee("개발자4", new Department("넷플릭스", JobType.DEVELOPER), 5000D);

		List<Employee> employees = Arrays.asList(e1, e2, e3, e4, e5);

		List<Integer> lengthList = employees.stream()
			.map(Employee::getName)
			.map(String::length)
			.collect(Collectors.toList());

		lengthList.stream()
			.forEach(System.out::println);
	}

}
