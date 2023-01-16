# 함수형 Builder 패턴

함수형 프로그래밍이 아닌 일반 자바에서의 `Builder` 패턴과 함수형 프로그래밍을 사용할 경우의 `Builder` 패턴을 정리해봤다. 빌더패턴이라는 것이 개념을 정리할 정도로 어려운 패턴은 아니다. 하지만, 함수형 프로그래밍 방식의 Builder 패턴은 공부하다보니 신기했고, 조금 특이하다. 그래서 정리했다. <br>

워낙 간단한 패턴이다 보니, 설명은 생략하는 게 낫겠다.<br>

만약 자바 개발 3년차 이상이면서 아직까지 빌더 패턴을 공부해본 적도 없고 직접 만들어본적이 없다면, 적어도 연습을 한번 정도는 좀... 해봐야 하지 않을까 싶다.<br>

롬복이 정말 좋은 라이브러리이지만, 롬복에만 의지하면 안된다.<br>

공통모듈을 개발하는 경우 보통, 라이브러리 의존을 최소한도로 해서 순수 자바로 해결하는 경우가 자주 있기 때문.<br>

<br>

> 최근 강의를 듣고 있는 강의를 듣다보니(?) 외국의 경우는 보통 학부레벨에서 빌더 패턴, 싱글턴 패턴 등등… 기본적인 디자인패턴은 모두 배우는 듯 해보였다. 이 말은 즉, 오늘 정리하는 내용은 그냥 기본일 뿐이라는 이야기.<br>
>
> 현업에서 과장급 개발자도 싱글턴이 뭔지, 빌더패턴이 뭔지, 불변객체가 뭔지, static 클래스는 뭔지 모르는 경우를 아주 드물게 본것 같다. 물론 이 분은 매일 술 퍼마시는 사람이라 기술공부에 관심자체가 없던 분이었지만… 이런 사람이 코테 공부해서 일반 상용 서비스회사로 이직해서 빌런처럼 구는걸 상상하면 좀 끔찍하다.<br>
>
> 우리나라 교수님들을 폄하하고 싶지는 않지만, 나도 학교에서는 전통적인 컴공교육은 못받은것 같다. 우리나라 학생들은 혼자 기본기를 어떤걸 공부하는지를 찾아서 혼자서 직접 내용을 찾아서 공부해야 하는 듯하다.

<br>

# 전통적인 Java 의 Builder 패턴

전통적인 자바의 Builder 패턴은 아래와 같다.<br>

엄청 긴 편은 아니지만, 함수형 으로 빌더패턴을 구현하면 훨씬 짧아진다.<br>

이번에도 설명은 생략. 간단하기에 직접 코드를 작성해보면 된다.<br>

```java
package io.study.lang.javastudy2022ty1.functional_study.builder;

import java.math.BigDecimal;

public class Employee1 {
    String id;
    String name;
    BigDecimal salary;

    public Employee1(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.salary = builder.salary;
    }

    public static Builder builder(String id, String name){
        return new Builder(id, name);
    }

    public static class Builder{
        private String id;
        private String name;
        private BigDecimal salary;

        public Builder(String id, String name){
            this.id = id;
            this.name = name;
        }

        public Builder(String id, String name, BigDecimal salary){
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withSalary(BigDecimal salary){
            this.salary = salary;
            return this;
        }

        public Employee1 build(){
            return new Employee1(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee1{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", salary=").append(salary);
        sb.append('}');
        return sb.toString();
    }
}
```

<br>

테스트 코드는 아래와 같다. 특이한 것은 없다.

```java
@Test
public void LEGACY_BUILDER_PATTERN(){
    Employee1 curry = Employee1.builder(UUID.randomUUID().toString(), "Curry")
            .withSalary(BigDecimal.valueOf(10002003000L))
            .build();

    System.out.println(curry);
}
```

<br>

출력결과

```plain
Employee1{id='9e5f1181-9c84-473d-a810-f99d04f80a83', name='Curry', salary=10002003000}
```

<br>

# 함수형 프로그래밍에서의 Builder 패턴

함수형 프로그래밍으로 위의 빌더패턴을 구현하는 경우를 보자. 너무 간단해져서 필드를 2개정도 더 추가해줬다.<br>

코드를 보면 `with[필드명](필드)` 로 지정하던 코드들이 모두 사라지고, `with(Consumer<Builder> builder)` 로 공통화되었음을 알 수 있다.<br>

<br>

```java
package io.study.lang.javastudy2022ty1.functional_study.builder;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class Employee2 {
    private String id;
    private String name;
    private BigDecimal salary;
    private String phoneNumber;
    private Integer nbaFinalCount;

    public Employee2(Builder builder){
        id = builder.id;
        name = builder.name;
        salary = builder.salary;
        phoneNumber = builder.phoneNumber;
        nbaFinalCount = builder.nbaFinalCount;
    }

    public static Builder builder(String id, String name){
        return new Builder(id, name);
    }

    public static class Builder{
        private String id;
        private String name;
        public BigDecimal salary;
        public String phoneNumber;
        public Integer nbaFinalCount;

        public Builder(String id, String name){
            this.id = id;
            this.name = name;
        }

        public Builder with(Consumer<Builder> builder){
            builder.accept(this);
            return this;
        }

        public Employee2 build(){
            return new Employee2(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee2{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", salary=").append(salary);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", nbaFinalCount=").append(nbaFinalCount);
        sb.append('}');
        return sb.toString();
    }
}
```

<br>

테스트 코드

```java
@Test
public void FUNCTIONAL_BUILDER_PATTERN(){
    Employee2 curry = Employee2.builder(UUID.randomUUID().toString(), "Curry")
            .with(builder -> {
                builder.salary = BigDecimal.valueOf(9999999999999999L);
                builder.nbaFinalCount = 4;
                builder.phoneNumber = "999-9999-9999";
            })
            .build();

    System.out.println(curry);
}
```

<br>

출력결과

```plain
Employee2{id='ec314e80-e9ad-49e4-b6be-ea6ba359ee49', name='Curry', salary=9999999999999999, phoneNumber='999-9999-9999', nbaFinalCount=4}
```

<br>

