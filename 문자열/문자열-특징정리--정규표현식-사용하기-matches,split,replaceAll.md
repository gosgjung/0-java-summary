# 문자열 정규표현식 사용하기 - `matches`, `split`, `replaceAll` 

`String` 클래스는 정규표현식 처리를 지원하기 때문에 `String` 클래스의 `matches` , `split` , `replaceAll` 을 그대로 사용할 수도 있다. 하지만 `String` 클래스를 로그 처리 등 대량의 문자열을 순회하는 경우에는 `String` 클래스는 매번 `Pattern` ,`Matcher` 클래스의 인스턴스가 매번 생성하여 처리를 수행하기에 처리 속도가 조금 늦어질 가능성이 있다.<br>

특히 `Pattern` 클래스의 객체 생성시에는 정규표현식 패턴을 컴퓨터가 처리하기 쉽게끔 변환하는데, 이런 객체 생성이 단순반복으로 자주 반복될 수 밖에 없는 로직 내에서 사용된다면, `Pattern` 클래스의 객체를 직접 생성하고, 재사용하게끔 로직을 작성해야 한다.<br>

<br>

`String` , `Pattern` , `Matcher` 를 사용하는 경우는 아래와 같이 분류할 수 있다.

- 문자열 처리를 일회성으로 할 경우
  - `String` 클래스로 단순하게 처리한다.
  - ex) 주로 애플리케이션 로딩 시의 인수 처리 등등
- 잦은 문자열 처리를 하는 경우
  - `Pattern` , `Matcher` 클래스의 객체를 직접 생성해서, 이 객체를 재사용하도록 구현한다.
  - ex) 로그 파일 처리 등등

<br>

## `String` 클래스를 사용해 정규표현식 처리

`String` 클래스로 정규표현식의 처리를 `matches` , `split` , `replaceAll`  메서드로 처리하는 예제다. 가급적 단순한 구조이거나, 자주 반복되지 않는 일회성 로직에서만 사용하는 것이 낫다.

```java
@Test
@DisplayName("String을 이용한 단순 정규표현식 살펴보기")
public void test1(){
    String line = "This            is scocer.";

    // 1 ) Th 뒤에 어떤 문자열이든 문자열이 있는지 체크
    // 2 ) 중간에 문자열 is 가 있으면서
    // 3 ) 문장의 가장 마지막을 . 으로 마무리 하는지 체크
    System.out.println("matches 테스트 >> ");
    boolean test = line.matches("Th.* is .*\\.");
    System.out.println("line.matches ? " + test);
    System.out.println("");

    System.out.println("split 테스트 >> ");
    String[] splitArr = line.split("\\s+");
    for (String word : splitArr){
        System.out.println("word = " + word);
    }
    System.out.println();

    System.out.println("replaceAll 테스트 >> ");
    String replacedString = line.replaceAll("\\s+", "+");
    System.out.println("replaced String = " + replacedString);
}
```

<br>

## `Pattern`, `Matcher`

`Pattern` 클래스는 정규표현식을 입력해 정규표현식을 가지고 있는 객체로 생성할 수 있다. `Pattern` 인스턴스로 특정 문장에 대해 현재 객체가 바인딩하고 있는 정규표현식에 부합하는 `Matcher` 객체를 생성할 수 있다.  `Matcher` 클래스는 어떤 문자열이 정규표현식에 일치하는지(`match`)를 검사할 수 있다. 또는 정규표현시에 매치(`match`)해서 특정 단어A를 다른 단어 B로 바꾸는 것 역시 가능하다.<br>

<br>

### Matcher.matches

정규 표현식을 가지고 있는 `Pattern` 클래스로는 `Matcher` 인스턴스를 생성할 수 있다. `Matcher` 클래스로는 특정 문자열이 정규 표현식에 일치하는지 검사할 수 있다. 아래는 정규 표현식에 특정 문자열이 매칭 되는지 검사하는 예제다.<br>

<br>

```java
@Test
@DisplayName("Matcher.matches")
public void test2(){
    // 'Ths is ' 뒤로는 어떤 문자열이든 올 수 있고 "." 으로 문장이 끝나야 한다.
    Pattern pattern = Pattern.compile("This is .*\\.");

    String line = "This is soccer.";

    // Pattern 객체로 특정 정규표현식에 대한 Matcher 객체 생성
    Matcher matcher = pattern.matcher(line);

    if(matcher.matches()){
        System.out.println("정규표현식이 일치합니다.!!!");
    }
    else{
        System.out.println("정규표현식을 다시 확인 부탁드립니다.");
    }
}
```

<br>

출력결과

```plain
정규표현식이 일치합니다.!!!
```

<br>

### Pattern.split

 `compile` 메서드를 통해 생성한 `Pattern` 객체는 특정 문자열에 대한 특정 정규표현식 파싱에 최적화된 `Pattern` 객체를 생성할 수 있다. 이렇게 생성된 `Pattern` 객체로 아래와 같이 문자열을 `split` 하는 것 역시 가능하다.<br>

<br>

```java
@Test
@DisplayName("Pattern.split")
public void test3(){
    // 정규표현식 : 공백
    Pattern pattern = Pattern.compile("\\s+");

    String line = "This is               soccer.";

    String[] split = pattern.split(line);
    System.out.println(Arrays.toString(split));
}
```

<br>

출력결과

```plain
[This, is, soccer.]
```

<br>

### Matcher.replaceAll

특정 문자열을 특정 정규표현식에 대해 컴파일된 `Pattern` 객체로 생성한 `Matcher` 객체는 문자열을 정규표현식을 이용해 정규표현식에 일치하는 모든 문자들을 치환(`replaceAll`) 하는 것이 가능하다.<br>

<br>

```java
@Test
@DisplayName("Matcher.replaceAll")
public void test4(){
    // 정규표현식 : 공백
    Pattern pattern = Pattern.compile("\\s+");

    String line = "This is               soccer.";

    Matcher matcher = pattern.matcher(line);

    String replacedLine = matcher.replaceAll("+");

    System.out.println(replacedLine);
}
```

<br>

출력결과

```plain
This+is+soccer.
```

<br>
