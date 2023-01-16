# 예제정리- BigDecimal 소수점 처리

setScale 함수를 사용해서 소수점 버림, 올림, 반올림,반내림 등의 처리를 할 수 있다.<br>

주의할 점은 setScale 함수의 반환하는 새로운 BigDecimal 객체로 받아서 사용해야 한다.<br>

객체의 불변성이 훼손하지 않고 새로운 객체를 리턴해준다는 점은 정말 깔끔한 것 같다.<br>

<br>

```java
@Test
public void test(){
    BigDecimal a1 = new BigDecimal("1222221111.11122222");
    BigDecimal a2 = a1.setScale(2, RoundingMode.FLOOR);
    BigDecimal a3 = a1.setScale(3, RoundingMode.HALF_EVEN);

    System.out.println("a1 = " + a1);
    System.out.println("a2 = " + a2);
    System.out.println("a3 = " + a3);
}
```

<br>

자세한 사용법은 아래의 참고자료들을 확인하자.

- [BigDecimal (Java Platform SE 8 )](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html)
- [Java.math.BigDecimal.setScale() Method](https://www.tutorialspoint.com/java/math/bigdecimal_setscale_rm_roundingmode.htm)

<br>

