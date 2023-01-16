# Supplier, Consumer, BiConsumer



# Supplier

공급자 역할. 입력 매개변수 없이 리턴값만 가진다.

```java
@FunctionalInterface
public interface Supplier<T>{
	T get();
}
```

<br>

## 예제 1) 단순 Supplier

`Supplier` 는 입력값 없이 람다 내부에서 값을 생산해내는 구문을 작성할 때 사용한다.

```java
@Test
public void TEST_SUPPLIER_SIMPLE(){
    Supplier<String> helloWorldSupplier = ()-> "hello world~~~~~";

    assertThat(helloWorldSupplier.get())
            .isEqualTo("hello world~~~~~");

    Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();

    IntStream.range(1, 10)
            .forEach(i -> System.out.println(uuidSupplier.get()));
}
```

<br>

## 예제 2) Supplier 를 인자 값으로 전달

이번에는 메서드에 `Supplier` 를 인자값으로 넘겨서 특정 동작을 수행하도록 구현

```java
@Test
public void TEST_SUPPLIER_USING_FUNCTION(){
    Supplier<String> uuidSupplier = () -> UUID.randomUUID().toString();
    printUUID(10, uuidSupplier);
}

public void printUUID(int count, Supplier<String> supplier){
    IntStream.range(1, count+1)
            .forEach(i -> System.out.println(supplier.get()));
}
```

<br>

# Consumer

입력 값을 받아서 특정동작을 수행하기만 하고 리턴값은 없는 경우에 사용.<br>

말 그대로 소비하는 역할만 수행.<br>

한국말로는 흡수한다는 말이 더 잘어울리기도 하는것 같다.<br>

```java
@FunctionalInterface
public interface Consumer<T> {
	void accept(T t);
}
```

<br>

## 예제 1) 단순 Consumer 사용

```java
@Test
public void TEST_CONSUMER_SIMPLE(){
    Consumer<String> printStringConsumer = System.out::println;
    printStringConsumer.accept(UUID.randomUUID().toString());
}
```

<br>

출력결과

```plain
b65240fa-05c7-4f0f-8e51-75703a955bdd
0f2245d5-0ae3-4d44-9b94-c8e89ca178cd
b93eac70-89ac-41cc-add7-bb6193359ce5
c9138051-f438-4dfa-8baa-30c76899c965
6313fc52-d41e-454c-abf7-28d0d51481f9
```

<br>

## 예제 2) `List<String>` 을 소비하는 예제

```java
@Test
public void TEST_CONSUMER_SIMPLE1(){
    List<String> uuidList = Arrays.asList(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
    );

    Consumer<String> printStringConsumer = str -> System.out.println(str);

    printUUIDList(uuidList, printStringConsumer);
}

public void printUUIDList(List<String> uuidList, Consumer<String> stringConsumer){
    uuidList.stream().forEach(uuid -> stringConsumer.accept(uuid));
}
```

<br>

출력결과

```plain
3f6766eb-3b5d-449c-9cb2-96ccf98a2373
99761937-4ab8-433d-ad03-ac900afc6038
ce2c84b7-3a80-489b-9bd0-cf0de1822fba
c08374c5-390d-4900-a008-b1b7bf25ec09
38bb162a-cac9-492a-a6da-4e2ca4994be5
```

<br>

## 예제 3) 제너릭을 사용해 consume 하기

이번에는 `Integer` 타입도 소비할 수 있도록 Generic을 사용해 consume 해보자.

```java
@Test
public void TEST_CONSUMER_SIMPLE2(){
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    Consumer<Integer> numberConsumer = number -> System.out.println(number);
    printAnyTypeList(numbers, numberConsumer);
}

public static <T> void printAnyTypeList(List<T> list, Consumer<T> consumer){
    list.stream().forEach(element -> consumer.accept(element));
}
```

<br>

출력결과

```plain
1
2
3
4
5
```

<br>

# BiConsumer

예제를 떠올리기 쉽지 않았다. 억지로 끼워맞추다 보니 조금 허술한 예제가 되었다.

```java
@Test
public void TEST_PRINT(){
    List<String> uuidList = List.of(
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString(),
        UUID.randomUUID().toString()
    );

    BiConsumer<String, Long> biConsumer = (s, n) -> {
        StringBuilder builder = new StringBuilder();

        builder.append("data : ").append(s).append(",  ")
                .append("time(ms) : ").append(n);

        System.out.println(builder.toString());
    };

    print(uuidList, biConsumer);
}

public static <T> void print(List<T> data, BiConsumer<T, Long> biConsumer){
    data.forEach(uuid -> {
        biConsumer.accept(uuid, System.nanoTime());
    });
}
```

<br>

출력결과<br>

```plain
data : e47b698c-6511-4025-9238-86f91fc6235f,  time(ms) : 84781364759100
data : 9e0587e7-9736-4363-a210-616f477a4a3e,  time(ms) : 84781364861000
data : 29e2dc97-8a8c-4433-aa32-e910eb8c4282,  time(ms) : 84781364881400
data : 710443e8-5a01-43c2-8df2-7922cf5ae264,  time(ms) : 84781364894900
```









