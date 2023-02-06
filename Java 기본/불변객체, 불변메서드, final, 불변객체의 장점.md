# 불변객체, 불변메서드, final, 불변객체의 장점

잠깐 불변객체 관련해서 정리할 일이 있어서 잠시 정리를 해두었다.<br>

원래는 Java 디렉터리에 옮겨야 하는데, 요즘 상황이 여러 곳에 관심을 두면서 신경쓰기 쉽지 않은 상황이다. 하나에 집중해야 하는 상황이라 일단 kotlin 스터디해두는 디렉터리에서 같이 버전관리해두기로 했다. 나중에 못찾을까봐 두렵긴 한데... 현재 가장 자주 들르는 디렉터리가 이곳이라 꼭 발견해서 다시 정리해둘 수 있지 않을까 싶다.<br>

<br>



### 불변객체란?

불변 객체는 객체가 생성된 이후로 내부의 상태가 변하지 않는 객체를 의미한다.

primitive type 의 객체의 경우 final 키워드를 붙이면 불변 객체로 선언 가능하다.

참조타입(POJO)타입의 객체는 final 로 선언하더라도 불변(immutable)임을 보장할수 있도록 부가적으로 추가적인 작업들을 꼭 해줘야 불변객체임을 보장할 수 있다.<br>

<br>



### 불변객체를 사용해서 얻는 장점

- Thread Safe
- 실패 원자적인 메서드를 만들 수 있다.
- 부수효과를 피할수 있다.
- 가비지 컬렉션 성능을 올릴 수 있도록 도와준다.
  - 가비지 컬렉터가 스캔하는 객체수가 줄어들기에 GC 수행시 지연시간이 줄어든다.

<br>



### 불변 객체가 가비지 컬렉션의 성능을 높일 수 있는 이유는?

참고자료 : [Java 불변 객체](https://velog.io/@bey1548/JAVA-%EB%B6%88%EB%B3%80%EA%B0%9D%EC%B2%B4)

<br>

불변객체를 생성할 때 내부적으로 수행되는 작업은 이렇다. 

1\) 먼저 해당 타입의 객체를 생성한다. 

2\) 해당 타입에 대한 컨테이너 객체로 ImmutableHolder 객체를 생성한다. 이렇게 생성된 ImmutableHolder 객체를 보통 **컨테이너**라고 이야기한다.

3\) 이렇게 생성된 ImmutableHolder 컨테이너 객체는 항상 해당 타입에 대한 value 객체를 참조한다.

<br>

GC 수행시 가비지 컬렉터는 이렇게 생성된 컨테이너 하위의 불변객체들을 스킵한다. 해당 타입에 대한 ImmutableHolder 라는 불변 객체 컨테이너가 살아있다는 것은 결국 해당 불변 객체 컨테이너 내의 모든 객체들은 처음에 할당된 그 상태로 참조되고 있음을 보장한다는 것을 의미한다. 

가비지 컬렉터는 이런 불변객체에 대한 컨테이너 하위의 객체들은 스캐닝을 스킵하게 된다. 결국 불변객체를 사용하면 가비지 컬렉터가 스캔해야 하는 객체의 수가 줄어들게 된다. 따라서 GC가 수행되더라도 지연시간을 줄일 수 있다.<br>

<br>

이 때 GC 가 수행될때 가비지 컬렉터는 컨테이너 하위의 불변객체들은 skip 할 수 있도록 도와준다. 해당 불변 컨테이너 객체 (ImmutableHolder) 가 살아있다는 것은 하위의 객체들도 모두 처음에 할당된 그 상태로 참조되고 있다는 것을 의미하기에, 가비지 컬렉터는 이 컨테이너 하의의 불변객체들을 스캐닝을 덜 하고, skip 하게 된다.<br>

따라서, 불변객체를 사용하면 가비지컬렉터가 스캔해야 하는 객체의 수가 줄어서 스캔해야 하는 메모리의 영역과 빈도수도 줄어든다. 따라서 GC가 수행되어도 지연시간을 줄일 수 있다.<br>

<br>



### 참조타입에 대한 불변객체 생성시 해주는 부가적인 작업들

일반 객체 타입에 대해 불변임을 보장할 수 있도록 작업을 해줄때 그 객체가 일반객체일 경우와 배열/리스트 같은 자료구조일 경우가 있는데 각각에 대한 처리방식을 정리해보면 아래와 같다.<br>



1\) 일반 객체에 대한 불변 처리

- 객체를 사용하는 필드의 참조변수도 모두 불변객체로 변경해준다.

<br>



2\) 참조변수가 배열/리스트 등의 자료구조일 경우

흔히 방어적 복사(defensive-copy)라고 하는 방식인데, 내부의 값들을 모두 복사해서 전달하는 방식을 이야기한다.

- 배열일 경우 배열을 받아서 copy 해준다. getter에서는 clone 메서드를 통해 반환하도록 처리해준다.
- 리스트일 경우 새로운 List 를 만들고 그 안에 복사한 값들로 차례로 채워준다.

<br>



### 불변클래스란?

불변 클래스는 아래의 규칙 네가지를 모두 따르는 클래스를 불변클래스라고 한다.

- final 로 선언한 클래스
- 클래스 내의 모든 변수를 private final 로 선언
- 객체를 생성하기 위한 생성자, 정적 팩토리를 정의해둔 클래스
- 현재 클래스의 어떤 메서드가 반환하는 필드가 참조에 의한 변경 가능성이 있는 경우의 필드일 경우 방어적 복사를 수행하도록 유도하는 클래스

<br>



#### e.g. ImmutableClass.java

단순한 예를 들어보면 아래와 같은 클래스다.

```java
public final class ImmutableClass{
    private final int age;
    private final String name;
    private final List<String> list;
    
    private ImmutableClass(int age, String name){
        this.age = age;
        this.name = name;
        this.list = new ArrayList<>();
    }
    
    public static ImmutableClass of(int age, String name){
        return new ImmutableClass(age, name);
    }
    
    public int getAge(){return age;}
    public String getName(){return name;}
    public List<String> getList() {
        return Collections.unmodifieableList(list);
    }
}
```

<br>



정적 팩토리 메서드

- 내부 생성자를 만드는 대신 객체의 생성을 위해 정적 팩토리 메서드를 정의했다.
- 또한 List와 같은 컬렉션 타입의 경우 list를 방어적 복사를 통해 제공했다.

<br>

기본생성자의 위험함 -> 기본생성자를 private으로 지정하고 정적 팩토리 메서드로 제공

- Java 는 생성자를 선언하지 않으면 기본 생성자가 자동으로 생성된다.
- 이 경우 다른 클래스에서 기본생성자를 자유롭게 호출가능하게 된다.
- 이런 경우에 대해 정적 팩토리 메서드를 통해 객체를 생성하도록 강제하면 좋다.

<br>

참조를 통해 변경이 가능한 경우 방어적 복사

- List와 같은 타입의 경우 객체 생성시 list 를 방어적 복사를 통해 생성

<br>



### final 클래스란?

상속을 금지시킨 클래스다.<br>

어떤 클래스를 다른 프로그래머가 상속 받아서 사용하는 것을 금지시키고자 할 때 사용하는데, 보통 보안상의 이유 때문에 사용한다.<br>

이론적으로는 중요한 class 의 subclass 를 만들어서 subclass 로 하여금 시스템을 파괴할 수 있기 때문에 Java 시스템은 중요한 class 에 대해 final 로 선언하고 있다. 예를 들면 String 클래스를 예로 들 수 있다.<br>

final class 로 선언되면 상속받을 수 없기 때문에 당연히 내부의 모든 method 는 overriding(재정의)될 수 없다.<Br>

<br>

#### e.g. StockEarning

주식 좋아하는 건 아니지만... 갑자기 떠오른게 주식이어서... '주식실적'이라는 의미의 객체를 예제로 사용. 실제 현업에서 사용되는 코드는 아니다. <br>

<br>

**StockEarning.java**

```java
public final class StockEarning{
    public BigDecimal netIncome;
    
    public void setNetIncome(BigDecimal netIncome){
        this.netIncome = netIncome;
    }
}
```

<br>

**SamsungEarning.java**

SamsungEarning 클래스가 StockEarning 클래스를 상속받고 있다. StockEarning 클래스는 상속이 불가능하다. 따라서 아래 코드는 컴파일 에러를 낸다.<br>

```java
public final class SamsungEarning extends StockEarning{ // 여기서 컴파일 에러가 난다. final 클래스는 상속받을 수 없다.
    public BigDecimal netIncome;
    
    public int getNetIncome(){
        return netIncome;
    }
}
```

<br>



### final 메서드

오버라이딩을 금지시킨 메서드를 의미한다. <br>

final 클래스가 아닌 일반 class 에서 특정 메서드만 오버라이딩할 수 없게 하려는 경우 해당 메서드에 final 키워드를 붙인다. 이렇게 하면 오버라이딩이 불가능해진다.<br>

final 메서드는 예를 들면 아래와 같은 경우에 사용된다.

- 부모 클래스에서 정의한 method 의 기능을 자식 클래스에서 그대로 사용하도록 강제할 경우

<br>

#### e.g.

StockEarning 클래스를 상속받은 SamsungEarning 클래스가 순이익을 수정하는 메서드를 오버라이딩 하고 있다. 그런데 실적을 계산하는 함수는 오버라이딩 되면 안된다. 회계관련 코드는 조작되면 안되기 때문이다. 여기에 관련된 예제를 정리해보면 아래와 같다.<br>

<br>



**StockEarning.java**

```java
public final class StockEarning{
    public BigDecimal netIncome;
    
    public final void setNetIncome(BigDecimal netIncome){
        this.netIncome = netIncome;
    }
}
```

<br>

**SamsungEarning.java**

```java
public final class SamsungEarning extends StockEarning{
    public BigDecimal netIncome;
    
    public final int setNetIncome(BigDecimal netIncome){ // 여기서 에러를 낸다.
        		// 위의 StockEarning 클래스의 setNetIncome(BigDecimal bigDecimal) 메서드는
        		// final 메서드로 선언되었기 때문에 오ㅓ라이딩이 불가능하다.
        		// '회계를 조작하면 안된다.' 라는 의미로 만들어봤다.
        return netIncome;
    }
}
```

<br>

