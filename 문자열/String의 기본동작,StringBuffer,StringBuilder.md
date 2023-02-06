### String 의 기본동작, StringBuffer, StringBuilder



### 참고자료

- [What is String Pool? - journaldev.com](https://www.digitalocean.com/community/tutorials/what-is-java-string-pool)

- [Item 63 - 문자열 연결은 느리니 주의하라](https://github.com/gosgjung/study-effective-java-3rd/blob/master/ITEM-63-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%97%B0%EA%B2%B0%EC%9D%80-%EB%8A%90%EB%A6%AC%EB%8B%88-%EC%A3%BC%EC%9D%98%ED%95%98%EB%9D%BC.md)
- [문자열 특징정리 - 문자열 결합하기 및 성능측정](https://github.com/gosgjung/0-java-summary/blob/main/%EB%AC%B8%EC%9E%90%EC%97%B4/%EB%AC%B8%EC%9E%90%EC%97%B4-%ED%8A%B9%EC%A7%95%EC%A0%95%EB%A6%AC--%EB%AC%B8%EC%9E%90%EC%97%B4-%EA%B2%B0%ED%95%A9%ED%95%98%EA%B8%B0-%EB%B0%8F-%EC%84%B1%EB%8A%A5%EC%B8%A1%EC%A0%95.md)

<br>



### new String() 과 리터럴("")의 차이점

**""(리터럴)** 방식은 Heap 내부의 Constant Pool 영역에 저장되며, 이미 만들어진 문자열이 있다면 새로 문자열 객체를 만들지 않고 Constant Pool 영역에 이미 저장되어 있는 것을 재할용한다. Constant Pool 은 Heap 영역에 위치해있다.<br>

**new String()** 방식은 Heap 영역에 문자열을 새로 만들어서 저장한다. 생성시마다 새로운 객체를 생성한다.

<br>

![1](C:\Users\soong\workspace\gosgjung\1-job-interview\img\JAVA\1.png)

출처 : [What is String Pool? - journaldev.com](https://www.digitalocean.com/community/tutorials/what-is-java-string-pool)

> - 예전 출처 : https://www.journaldev.com/797/what-is-java-string-pool 
> - 현재 출처 : https://www.digitalocean.com/community/tutorials/what-is-java-string-pool 으로 옮겨져있다

<br>



### String 클래스의 intern() 메서드

String 클래스에는 intern() 이라는 메서드가 있다. 

intern() 메서드는 만약 같은 값을 가진 **String 객체가 이미 String Constant Pool 내에 존재하면 이미 존재하는 해당 객체를 그대로 리턴**해준다. 

만약 같은 값을 가진 **String 객체가 String Constant Pool 내에 존재하지 않으면 새로 문자열 객체를 생성**해서 String Constant Pool 에 저장하고 생성한 문자열 객체의 reference 를 리턴한다.

<br>



**e.g.**

```java
@Test
void intern_메서드_테스트(){
    String str1 = "안녕하세요";
    String str2 = new String("안녕하세요");
    assertThat(str1).isNotSameAs(str2);
    System.out.println("str1 == str2 ? " + (str1 == str2));

    assertThat(str1).isSameAs(str2.intern());
    System.out.println("str1 == str2 ? " + (str1 == str2.intern()));
}
```

<br>



**출력결과**

```plain
str1 == str2 ? false
str1 == str2 ? true

Process finished with exit code 0
```

<br>



### String 객체가 불변으로 제공되는 것의 장점

- Thread Safe

- 보안

<br>

**Thread Safe**<br>

String 객체를 불변으로 제공하면, 여러 쓰레드에서 어떤 특정 String 객체를 동시에 접근해도 안전하다.<br>

<br>

**보안**<br>

중요한 데이터를 문자열로 다루는 경우 현재 참조하고 있는 문자열 값을 변경해서 다른 스레드에서의 관련된 로직에 사이드이펙트를 발생시키지 않는 다는 점은 장점이다.<br>

<br>



### String 객체가 불변으로 제공되는 것의 단점보완 - String Constant Pool

메모리 절약/성능 최적화를 위한 캐싱처리

- Java 의 String 객체들은 Heap 메모리영역의 String Constant Pool 이라는 영역에 저장된다.
- 이 때, 참조하려는 문자열이 String Constant Pool 에 존재할 경우, 새로 생성하지 않고 String Constant Pool 에 있는 객체를 사용한다.
- 따라서 특정 문자열 값을 재사용하는 빈도가 높을 수록 성능 향상을 기대할 수 있다.

<br>



### 문자열 연결은 느리다. 주의가 필요하다. (Effective Java, 자바퍼즐러)

> 참고) Effective Java, 자바 퍼즐러

- [Item 63 - 문자열 연결은 느리니 주의하라](https://github.com/gosgjung/study-effective-java-3rd/blob/master/ITEM-63-%EB%AC%B8%EC%9E%90%EC%97%B4-%EC%97%B0%EA%B2%B0%EC%9D%80-%EB%8A%90%EB%A6%AC%EB%8B%88-%EC%A3%BC%EC%9D%98%ED%95%98%EB%9D%BC.md)
- [문자열 특징정리 - 문자열 결합하기 및 성능측정](https://github.com/gosgjung/0-java-summary/blob/main/%EB%AC%B8%EC%9E%90%EC%97%B4/%EB%AC%B8%EC%9E%90%EC%97%B4-%ED%8A%B9%EC%A7%95%EC%A0%95%EB%A6%AC--%EB%AC%B8%EC%9E%90%EC%97%B4-%EA%B2%B0%ED%95%A9%ED%95%98%EA%B8%B0-%EB%B0%8F-%EC%84%B1%EB%8A%A5%EC%B8%A1%EC%A0%95.md)

<br>



**문자열 붙이기 100만건 수행시 시간측정** <br>

- String 의 + 연산을 사용할 때 : 72609ms , 72초
- String의 concat 연산을 사용할 때 : 63948ms , 63초
- StringBuilder : 10ms
- StringBuffer : 12ms

<br>





