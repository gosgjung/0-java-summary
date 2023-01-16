package io.study.modernjavainaction.ch3.ch3_8_useful_methods_to_combine_lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class Predicate_T {

	enum JobType{
		DEVELOPER(1000, "DEVELOPER"){},
		FIREFIGHTER(2000, "FIREFIGHTER"){},
		DJ(3000, "MUSIC DJ"){};

		private int jobTypeCode;
		private String jobTypeName;

		JobType(int jobTypeCode, String jobTypeName){
			this.jobTypeCode = jobTypeCode;
			this.jobTypeName = jobTypeName;
		}

		public int getJobTypeCode() {
			return jobTypeCode;
		}

		public void setJobTypeCode(int jobTypeCode) {
			this.jobTypeCode = jobTypeCode;
		}

		public String getJobTypeName() {
			return jobTypeName;
		}

		public void setJobTypeName(String jobTypeName) {
			this.jobTypeName = jobTypeName;
		}
	}

	class Employee {
		private String name;
		private JobType jobType;
		private BigDecimal salary;

		public Employee(String name, JobType jobType, BigDecimal salary){
			this.name = name;
			this.jobType = jobType;
			this.salary = salary;
		}

		@Override
		public String toString() {
			return "Employee{" +
				"name='" + name + '\'' +
				", jobType=" + jobType +
				", salary=" + salary +
				'}';
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

		public BigDecimal getSalary() {
			return salary;
		}

		public void setSalary(BigDecimal salary) {
			this.salary = salary;
		}
	}

	@Test
	void testPredicateNegate1(){
		Employee e1 = new Employee("developer1", JobType.DEVELOPER, new BigDecimal(90000000));
		Employee e2 = new Employee("developer2", JobType.DEVELOPER, new BigDecimal(100000000));
		Employee e3 = new Employee("developer3", JobType.DEVELOPER, new BigDecimal(200000000));
		Employee e4 = new Employee("fire fighter 1", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e5 = new Employee("fire fighter 2", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e6 = new Employee("fire fighter 3", JobType.FIREFIGHTER, new BigDecimal(90000000));
		Employee e7 = new Employee("fire fighter 4", JobType.FIREFIGHTER, new BigDecimal(70000000));

		List<Employee> employees = new ArrayList<>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		employees.add(e4);
		employees.add(e5);
		employees.add(e6);
		employees.add(e7);

		// 개발자가 아닌 직원들을 추려내기
		Predicate<Employee> isDeveloper = e -> {
			JobType developerType = JobType.DEVELOPER;
			return developerType.equals(e.getJobType());
		};

		Predicate<Employee> isNotDeveloper = isDeveloper.negate();

		employees.stream().filter(isNotDeveloper)
			.forEach(System.out::println);
	}

	@Test
	void testPredicateNegate2(){
		Employee e1 = new Employee("developer1", JobType.DEVELOPER, new BigDecimal(90000000));
		Employee e2 = new Employee("developer2", JobType.DEVELOPER, new BigDecimal(100000000));
		Employee e3 = new Employee("developer3", JobType.DEVELOPER, new BigDecimal(200000000));
		Employee e4 = new Employee("fire fighter 1", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e5 = new Employee("fire fighter 2", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e6 = new Employee("fire fighter 3", JobType.FIREFIGHTER, new BigDecimal(90000000));
		Employee e7 = new Employee("fire fighter 4", JobType.FIREFIGHTER, new BigDecimal(70000000));

		List<Employee> employees = new ArrayList<>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		employees.add(e4);
		employees.add(e5);
		employees.add(e6);
		employees.add(e7);

		Predicate<Employee> isDeveloper = e -> {
			JobType developerType = JobType.DEVELOPER;
			return developerType.equals(e.getJobType());
		};

		Predicate<Employee> salaryLimitIsLarger = e -> {
			BigDecimal salaryLimit = e.getSalary();
			return salaryLimit.doubleValue() >= 100000000;
		};

		// 연봉이 1억 이상이면서 직업이 개발자가 아닌 사람을 사람을 추려내기
		Predicate<Employee> filterOption = salaryLimitIsLarger.and(isDeveloper.negate());
		List<Employee> filtered = employees.stream()
										.filter(filterOption)
										.collect(Collectors.toList());

		filtered.stream().forEach(System.out::println);
	}

	@Test
	void testPredicateOr1(){
		Employee e1 = new Employee("developer1", JobType.DEVELOPER, new BigDecimal(90000000));
		Employee e2 = new Employee("developer2", JobType.DEVELOPER, new BigDecimal(100000000));
		Employee e3 = new Employee("developer3", JobType.DEVELOPER, new BigDecimal(200000000));
		Employee e4 = new Employee("fire fighter 1", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e5 = new Employee("fire fighter 2", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e6 = new Employee("fire fighter 3", JobType.FIREFIGHTER, new BigDecimal(90000000));
		Employee e7 = new Employee("Muic DJ #1", JobType.DJ, new BigDecimal(700000000));

		List<Employee> employees = new ArrayList<>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		employees.add(e4);
		employees.add(e5);
		employees.add(e6);
		employees.add(e7);

		Predicate<Employee> isDeveloper = e -> {
			JobType developerType = JobType.DEVELOPER;
			return developerType.equals(e.getJobType());
		};

		Predicate<Employee> isFireFighter = e -> {
			JobType fireFighterType = JobType.FIREFIGHTER;
			return fireFighterType.equals(e.getJobType());
		};

		Predicate<Employee> developerOrFireFighterFilter = isDeveloper.or(isFireFighter);

		employees.stream().filter(developerOrFireFighterFilter)
			.forEach(System.out::println);
	}

	@Test
	void testPredicateInline1(){
		Employee e1 = new Employee("developer1", JobType.DEVELOPER, new BigDecimal(90000000));
		Employee e2 = new Employee("developer2", JobType.DEVELOPER, new BigDecimal(100000000));
		Employee e3 = new Employee("developer3", JobType.DEVELOPER, new BigDecimal(200000000));
		Employee e4 = new Employee("fire fighter 1", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e5 = new Employee("fire fighter 2", JobType.FIREFIGHTER, new BigDecimal(200000000));
		Employee e6 = new Employee("fire fighter 3", JobType.FIREFIGHTER, new BigDecimal(90000000));
		Employee e7 = new Employee("Muic DJ #1", JobType.DJ, new BigDecimal(700000000));

		List<Employee> employees = new ArrayList<>();
		employees.add(e1);
		employees.add(e2);
		employees.add(e3);
		employees.add(e4);
		employees.add(e5);
		employees.add(e6);
		employees.add(e7);

		Predicate<Employee> isDeveloper = e -> {
			boolean developerFlag = JobType.DEVELOPER.equals(e.getJobType());
			return developerFlag;
		};

		Predicate<Employee> isDevleoperOrFirefighter = isDeveloper.or(e->{
			boolean isFireFiehter = JobType.FIREFIGHTER.equals(e.getJobType());
			return isFireFiehter;
		});

		employees.stream().filter(isDevleoperOrFirefighter)
			.forEach(System.out::println);
	}
}
