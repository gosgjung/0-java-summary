# @Builder, 꼼꼼하게 사용하자

- Builder 에 비어있는 값이 있을 경우를  체크하자.
- 클래스 상단의 @Builder 보다는 가급적 생성자 별도에 국한된 @Builder 를 사용하자.



# Builder 패턴의 허점

## 필수적으로 채워야 하는 값에 대해서 비어있는 값을 허용하는 경우

불안전한 객체를 만들어내는 경우가 있다. 예를 들어 은행 계좌를 표현하는 클래스  `Account1` 가 있다고 해보자. Account1 클래스에는 은행이름, 계좌명이 비어있는 문자열이면 안된다. 그런데, Builder 패턴에 아무런 장치(?)를 해두지 않고 그냥 사용할 경우 비어있는 문자열을 대입해도 객체 생성이 허용된다.

만약 아래의 코드 처럼 빌더를 구성했다면, `bankName` , `accountNumber` , `accountHolder` 가 필수적으로 채워져야 하는 값일 경우에도, Builder 메서드를 통해 비어있는 공백 문자열이 값으로 전달되면, 공백 문자로 채워지게 되는 허점이 생긴다.

아래 코드는 nullable 을 false로 지정해서 나름의 방어대책을 마련해두었지만, 그렇다고 해도, null 로 값이 채워지는 것 까지는 막지 못했다.

```java
package io.study.jpa.builder_must_limit.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account1 {

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Builder(builderMethodName = "defaultBuilder")
    public Account1(final String bankName,
                    final String accountNumber,
                    final String accountHolder){

        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
    }
}
```

<br>



`Account1` 을 실제로 null 이 입력되는 테스트 코드를 보자

```java
@Test
public void 나쁜예_Account1_객체의_Builder로_비어있는_값이_들어와도_nullable_false_가_성립되지_않는다(){
    Account1 account1 = Account1.defaultBuilder()
            .accountHolder("")
            .bankName("jp morgan")
            .accountNumber("111222333")
            .build();

    System.out.println(account1);

    assertThat(account1.getAccountHolder()).isEmpty();
}
```

비어있는 문자열이 `accountHolder` 로 들어왔는데도 정상적으로 수행되고 있다. 물론 null 값은 아니지만, 객체가 본인의 역할을 명확하게 수행하고 있지 못하다.<br>



출력결과를 보면 아래와 같이 나타난다.

```java
Account1[bankName='jp morgan', accountNumber='111222333', accountHolder='']
```

이 경우 객체 생성 이후에 부가적으로 비어있는 문자열을 체크하는 경우 역시 생각해볼 수 있다. 하지만, 굳이 부가적인 로직을 만드는 것 보다는, 객체가 수행해야 하는 책임을 분명히 하는 것이 필요하다.<br>

<br>



## 필드 값을 assert 하는 것을 통해, 유효성 체크를 수행하자.

위의 코드 까지는 불안전한 객체를 만드는 코드였다. 객체 자신의 책임을 다하지 못하고 있다. (bankName이 공백이고, accountNumber가 공백이다.)

@Column 어노테이션의 nullable 을 false로 주었지만 아무 소용이 없다.

이렇게 객체가 완전하지 못하면, 그 책임은 다른 객체에게로 넘어간다.

예를 들면 아래와 같은 코드로 null 체크를 명확하게 수행하면 좋다. 아래의 예에서는 SpringFramework 의 `Assert` 클래스 내의 `hasText()` 메서드로 유효성 체크를 수행했다.

```java
package io.study.jpa.builder_must_limit.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account2 {

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @Column(name = "account_holder", nullable = false)
    private String accountHolder;

    @Builder
    public Account2(final String bankName,
                    final String accountNumber,
                    final String accountHolder){

        Assert.hasText(bankName, "bankName must not be empty");
        Assert.hasText(accountNumber, "accountNumber must not be empty");
        Assert.hasText(accountHolder, "accountHolder must not be empty");

        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;

    }
}
```

<br>

Assert 클래스는 해당 필드가 null 이거나, 빈 문자열일 경우 `IllegalArgumentException` 을 발생시킨다. 예를 들면 아래 코드처럼 객체를 생성시 필수 값인 `accountHolder` 필드를 비운 채로 객체를 생성하는 코드를 작성하면 `IllegalArgumentException` 이 발생하게 된다.

```java
@Test
public void Account2_객체의_필수필드값인_accountHolder가_비어있는_값이면_에러를_내야한다(){
    BDDAssertions.thenThrownBy(()->{
        Account2.builder()
                .accountHolder("")
                .bankName("jp morgan")
                .accountNumber("1112223333")
                .build();
    }).isInstanceOf(IllegalArgumentException.class);
}
```