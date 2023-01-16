# Java 14 Record

> 참고자료 : [www.baeldung.com - Java 14 Record keyword](https://www.baeldung.com/java-record-keyword)<br>

<br>

Java 14 에서는 `Record` 라는 개념이 생겼다. 아직은 확실히 아는 것 같다는 느낌은 들지 않아서 자료 정리를 시작했다.<br>

<br>

## immutability

`immutability` 를 확보하기 위해서는 아래의 요건들이 충족되어야 한다.

- `private` , `final` 필드
- 각 필드에 대한 `getter` 
- 각 필드들에 대한 `public` 생성자
- `equals` 메서드
- `hashCode` 메서드
- `toString` 메서드

<br>

자바 14부터 `Record` 키워드가 적용된 객체는 위의 6가지 요소를 모두 만족한다.

<br>

## 고전적인 data 클래스

불변성을 지키기 위해 위의 6가지 조건을 만족시키는 클래스를 작성해보면 아래와 같다.

```java
public class RecordKeywordTest {

    @Test
    @DisplayName("고전적인 데이터 클래스")
    public void test1(){
        Item item1 = new Item("A book on C", new BigDecimal("10000"));
        Item item2 = new Item("B book on D", new BigDecimal("20000"));
        Item item3 = new Item("A book on C", new BigDecimal("10000"));

        assertThat(item1).isEqualTo(item3);
        assertThat(item1).isNotEqualTo(item2);
    }

    public class Item{
        private final String name;
        private final BigDecimal price;

        public Item(String name, BigDecimal price){
            this.name = name;
            this.price = price;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){
                return true;
            }
            else if(!(obj instanceof Item)){
                return false;
            }
            else{
                Item other = (Item) obj;
                return Objects.equals(name, other.name) && Objects.equals(price, other.price);
            }
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
```

그리고 테스트 코드를 돌려본 결과는 정상이다.<br>

하지만, 이 코드는 에러가 있는 것은 아니지만, 사용상에 불편함이 있다.<br>

- 보일러 플레이트 코드가 산재해 있다.
  - IDE 를 사용하는 것 (eclipse, Intellij)으로 해결이 가능하다.
- 클래스의 목적이 불분명하다.
  - name, price 를 가진 `Item` 을 표현하는 것은 클래스의 목적을 파악하기 쉽지 않다.
  - 부가적인 코드(toString, hashCode 등)가 이 클래스가 단순히 두개의 필드(`name` , `price` )를 가진 `data` 클래스임을 알아보기 어렵게(모호하게) 만든다.

<br>

## Record를 사용해보자

`Record` 는  java 14 에서 도입된 개념이다. 일반적으로 Lombok 을 사용하면 `toString` , `hashCode`, `eqauls`, `Getter/Setter` 등의 보일러 플레이트 코드를 쉽게 대체할 수 있다. 하지만, Java14 에서는 이런 롬복의 장점을 수용한 `Record` 라는 키워드를 도입했다. `record` 는 Lombok 처럼 어노테이션 기반은 아니다. 코틀린의 클래스와 생김새가 유사하다.<br>

가끔 테스트 스코프 내에서 Lombok을 적용하기 힘든 경우가 있다. 이런 경우에 적극적으로 `Record` 를 도입해보는 것은 어떨까? 최근에 테스트 코드에서 주로 사용하다가, 클래스 내부에서만 사용되는 자료형으로도 `record` 를 사용해봤는데, 편리하게 느껴졌었다. 동시성 + 병렬 환경에서도 thread safe 하다는 것이 `record` 객체의 장점이다.<br>

<br>

## Record 객체 생성

정말 기본적인 사용법은 아래와 같다.

```java
public record Item2(String name, BigDecimal price){}
```

<br>

위의 코드에서 생성되는 생성자는 아래와 같은 모습이 된다.<br>

```java
public Item2(String name, BigDecimal price){
    this.name = name;
    this.price = price;
}
```

<br>

그리고 객체 생성은 아래와 같이 하면 된다.<br>

```java
public record Item2(String name, BigDecimal price){}

@Test
@DisplayName("레코드 사용해보기 #1")
public void test2(){
    Item2 item = new Item2("A book on C", new BigDecimal("1000"));
    System.out.println(item);
}
```

<br>

## getter

`getter` 는 `getXXX` , `setXXX` 와 같은 형식이 아닌 `필드명()` 과 같은 형식으로 제공해준다. <br>

예를 들면 아래 코드에서 `name`, `price` 필드의 `getter` 는 각각 아래와 같다.

- `name` 의 getter : `item.name()`
- `price` 의 getter : `item.price()` 

<br>

```java
public record Item2(String name, BigDecimal price){}

@Test
@DisplayName("레코드 사용해보기 #1")
public void test2(){
    Item2 item = new Item2("A book on C", new BigDecimal("1000"));
    System.out.println(item);
}

@Test
@DisplayName("레코드 사용해보기 #2 = Getter 가 있는지 확인해보자.")
public void test3(){
    Item2 item = new Item2("A book on C", new BigDecimal("1000"));

    assertThat(item.name()).isEqualTo("A book on C");
    assertThat(item.price()).isEqualTo(new BigDecimal("1000"));
}
```

<br>

## equals

객체 구현시 equals 가 필요하기에 직접 작성하거나 `Lombok` 의 도움을 받는 경우가 있다. 코드로 확인해보자.<br>

```java
public record Item2(String name, BigDecimal price){}

// ...

@Test
@DisplayName("레코드 사용해보기 #3 = Equals 사용하기")
public void test4(){
    Item2 item1 = new Item2("A book on C", new BigDecimal("1000"));
    Item2 item2 = new Item2("A book on C", new BigDecimal("1000"));

    assertThat(item1).isEqualTo(item2);
}
```

<br>

## hashCode

`name`, `price` 필드가 항상 같은 값과, 같은 필드로 선언되어 있다면, 항상 같은 `hashCode` 를 리턴해야 한다.<br>

예제를 확인해보자.

```java
public record Item2(String name, BigDecimal price){}

// ...

@Test
@DisplayName("레코드 사용해보기 #4 = hashCode")
public void test5(){
    Item2 item1 = new Item2("A book on C", new BigDecimal("1000"));
    Item2 item2 = new Item2("A book on C", new BigDecimal("1000"));

    assertThat(item1.hashCode()).isEqualTo(item2.hashCode());
}
```

<br>

## toString

`record` 로 선언한 객체는 기본적으로 `toString` 을 제공해준다.<br>

```java
public record Item2(String name, BigDecimal price){}

// ...

@Test
@DisplayName("레코드 사용해보기 #5 = toString")
public void test6(){
    Item2 item1 = new Item2("A book on C", new BigDecimal("1000"));
    Item2 item2 = new Item2("A book on C", new BigDecimal("1000"));

    assertThat(item1.toString()).isEqualTo(item2.toString());
    System.out.println(item1.toString());
    System.out.println(item2.toString());
}
```

<br>

## 기본 생성자 커스터마이징

상황에 따라 다양하게 쓰일 수 있지만, 여기서 정리하는 예제는 유효성 체크에 초점을 맞춰서 가능한 간단한 코드로 예제를 정리해봤다.<br>

### 생성자 오버로딩

인자가 `name` 하나뿐인 생성자를 만들었다. 그리고, `price` 필드에 대해서는 아래와 같이 `this` 키워드를 통해 기본생성자를 이용해 기본값을 채워주도록 해주었다.<br>

```java
public record Item3(String name, BigDecimal price){
    public Item3{
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
    }

    public Item3(String name){
        this(name, new BigDecimal("0"));
    }
}

@Test
@DisplayName("레코드 사용해보기 #6 = 생성자 커스터마이징해서 사용해보기")
public void test7(){
    Item3 item1 = new Item3("A book on C");
    System.out.println(item1);
}
```

<br>

## static 메서드, static 필드 사용해보기

예제를 먼저 보자!!!<br>

```java
public record Item4(String name, BigDecimal price){
    public static String NO_NAME = "NO_NAME";
    public Item4{
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);
    }

    public static Item4 newItem4(String name, BigDecimal price){
        return new Item4(name, price);
    }

    public static Item4 newEmptyItem(String name){
        return new Item4(name, new BigDecimal("0"));
    }
}

@Test
@DisplayName("레코드 사용해보기 #7 = static 메서드, static 필드 사용해보기")
public void test8(){
    System.out.println(Item4.NO_NAME);
    System.out.println(Item4.newEmptyItem("A Book on C"));
}
```

<br>

