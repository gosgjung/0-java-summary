# 문자열 분할,결합,치환,검색 - split,join,replace,indexOf

## `String.split` 메서드 

특정 문자를 기준으로 해당 특정문자열이 나타난 곳마다 위치한 모든 단어들을 잘라내는 역할을 한다. 또는 정규표현식을 기준으로 분할하는 것 역시 가능하다.<br>

공백문자를 기준으로 문자열을 분할하는 예제다.

```java
@Test
@DisplayName("split 메서드")
public void test1(){
    String line = "This is               soccer.";

    String[] arr = line.split(" ");
    System.out.println("first case >> ");
    System.out.println(Arrays.toString(arr));
    System.out.println();

    String line2 = "This is soccer.";
    String[] arr2 = line2.split(" ");
    System.out.println("second case >> ");
    System.out.println(Arrays.toString(arr2));
    System.out.println();
}
```

<br>

출력결과

```plain
first case >> 
[This, is, , , , , , , , , , , , , , , soccer.]

second case >> 
[This, is, soccer.]
```

<br>

이번에는 정규표현식을 기준으로 문자열을 분할하는 예제다.

```java
@Test
@DisplayName("split 메서드 > 정규표현식으로 분할하기")
public void test2(){
    String url = "www.naver.com";

    String[] split = url.split("\\.");
    System.out.println(Arrays.toString(split));
}
```

<br>

출력결과

```plain
[www, naver, com]
```

<br>

## `String.join` 메서드 

예를 들면 아래와 같은 `List<String>` 이 있다고 해보자.

```java
List<String> players = Arrays.asList("손흥민", "황의조", "남태희", "황희찬");
```

이것을 아래와 같은 문자열로 바꿔야 하는 상황이 생겼다.

```plain
손흥민,황의조,남태희,황희찬
```

<br>

보통 이런 경우 가장 단순한 방법은 `for` 문과 `StringBuilder` 를 이용해서 문자열을 조합하면 된다. 하지만, `Java 8` 부터는 문자열들을 서로 연결할 수 있도록 표준 API 에서 `String.join()` 메서드를 제공하기 시작했다.<br>

`String.join()` 메서드는 단순히 `List<String>` 만을 하나의 문자열로 조합할 수 있는 것이 아니다. 가변인자를 받아서 가변인자를 하나의 문자열로 만들어내는 것 역시 가능하다.<br>

<br>

**예제 1) `List<String>` 의 각 요소를 `,` 로 구분된 문자열로 만들기**<br>

```java
@Test
@DisplayName("join 메서드 #1")
public void test3(){
    List<String> players = Arrays.asList("손흥민", "황의조", "남태희", "황희찬");
    String joinStr = String.join(",", players);
    System.out.println(joinStr);
}
```

<br>

**예제 2) 가변인자를 전달해 `,` 로 구분된 문자열로 만들기**<br>

```java
@Test
@DisplayName("join 메서드 #2")
public void test4(){
    String players = String.join(",", "손흥민", "황의조", "남태희", "황희찬");
    System.out.println(players);
}
```

<br>

## `String.replace` 메서드

특정 문자열을 다른 문자열로 치환하려 사용한다. 

```java
@Test
@DisplayName("replace 메서드")
public void test5(){
    String players = "손흥민,황의조,남태희,황희찬";
    String replace = players.replace(",", "+");
    System.out.println(replace);
}
```

<br>

## `String.indexOf` 메서드 - 문자열 검색

매우 간단한 예제다.

```java
@Test
@DisplayName("indexOf 메서드 #1")
public void test6(){
    String players = "손흥민,황의조,남태희,황희찬";
    int index = players.indexOf(",");
    System.out.println("indexOf(\",\") result >> ");
    System.out.println(index);
    System.out.println();

    int index2 = players.indexOf("#");
    System.out.println("indexOf(\"#\") result >> ");
    System.out.println(index2);
    System.out.println();
}
```

출력결과

```plain
indexOf(",") result >> 
3

indexOf("#") result >> 
-1
```

<br>

이번에는 특정인덱스 위치에서부터 문자열의 출현을 검사한다.

```java
@Test
@DisplayName("indexOf 메서드 #2 >> 특정위치에서부터 문자열의 출현을 검색하기 ")
public void test7(){
    String players = "손흥민,황의조,남태희,황희찬";
    int index1 = players.indexOf(",", 3);
    System.out.println(index1);
    int index2 = players.indexOf(",", 4);
    System.out.println(index2);
}
```

출력결과

```plain
3
7
```

<br>

## `String.lastIndexOf` 메서드

간단한 예제다.

```java
@Test
@DisplayName("lastIndexOf 메서드")
public void test8(){
    String players = "손흥민,황의조,남태희,네이마르";

    int lastIndex1 = players.lastIndexOf(",");
    System.out.println("lastIndex1 >> " + lastIndex1);

    // String.charAt(4) 에서부터 가장 마지막으로 ","가 나타나는 곳은 String.charAt(3) 이다.
    int lastIndex2 = players.lastIndexOf(",", 4);
    System.out.println("lastIndex2 >> " + lastIndex2);
    System.out.println("char >> " + players.charAt(lastIndex2+1));
}
```

<br>

출력결과

```plain
lastIndex1 >> 11
lastIndex2 >> 3
char >> 황
```

<br>















