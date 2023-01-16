# @Data 사용은 지양하자

예전에 노션에 정리해두고, 멍청하게도 깃헙에 옮겨둔줄 착각해왔던 나... 어떡하니...<br>

<br>

롬복으로 인해 자동으로 추가되는 기능들은 그 댓가를 치르게 된다.

객체는 본인의 역할과 책임을 그 의도를 분명하게 드러내는 것이 좋은 객체, 풍부한 객체

- `@Data` 는 지양하자



# @Data 는 지양하자

## @Data를 사용하는 것의 단점,부작용

- `@Setter` 를 무분별하게 남용하게 된다.
- `@ToString` 양방향 순환 참조 문제 발생
- `@EqualsAndHashCode` 를 남용하게 된다.

<br>



## **@Setter를 사용하는 것으로 인한 단점**

만약 email 변경 기능이 없다고 하면 email 을 변경하는 setter 코드도 없어야 하는 것이 객체 디자인 상으로 맞는 디자인이다.<br>

만약 @Setter 가 있다면, Memeber 객체는 email 을 변경할 수 있어… 라고 이야기하는 것이지만, Member 쪽의 서비스에는 email을 변경하는 기능이 없을 경우 기능의 요구사항과 setter 는 불일치하게 된다.<br>

<br>

**e.g. 쿠폰**<br>

쿠폰을 사용하면, used 를 TRUE 로 바꾸고 쿠폰의 사용 금액을 0원으로 바꾸는 동작을 하나로 취급해서 처리해야 한다.

이 경우 used와 사용 금액 처리를 모두 한쌍으로 처리해야 한다. 그런데 setter를 사용하면 의도치 않게 하나의 필드를 개별로 수정할 수 있게 되어버리는 단점, 부작용이 생긴다.

<br>



```java
package io.study.jpa.builder_must_limit.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, updatable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany
    @JoinColumn(name = "coupon_id")
    @ToString.Exclude
    private List<Coupon> couponList = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
```

<br>



Coupon

```java
package io.study.jpa.builder_must_limit.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "coupon")
@Data
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "used", nullable = false)
    private boolean used;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updateAt;
}
```

<br>



## @ToString 양방향 순환 참조 문제

```java
@Entity
@Table(name = "member")
@Data
public class Member{
	// ...
	@OneToMany
	@JoinColumn(name = "coupon_id")
	private List<Coupon> coupons = new ArrayList<>();

}

@Entity
@Table(name = "coupon")
@Data
public class Coupon{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	private Member member;

	public Coupon(Member member){
		this.member = member;
	}
}
```

<br>



테스트코드

```java
package io.study.jpa.builder_must_limit.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MemberTest {

    @Test
    public void SETTER_생성으로_인한_단점() {
        // 우리는 회원에 대한 이메일 변경 기능을 제공하지 않는다.
        final Member member = new Member();

        // setter 로 데이터를 채워나가는 방식을 'bean 방식' 이라고 부른다.
        member.setEmail("asd@asd.com");
        member.setName("name");
        // 객체 생성 완료
        // 앞으로 추가적인 이메일 변경은 불가능 해야한다.

        member.setEmail("new@asd.com");
        // 하지만, @Data 어노테이션을 사용하면 @Setter가 자동으로 추가되므로
        // 이메일 변경을 외부에서 위와 같이 할수 있게, 즉, 필드 수정을 외부에서 할수 있게 되어 버린다.
    }

    @Test
    // Member 클래스 내에 @Exclude 어노테이션을 주석 처리 후 실행하면
    // StackOverflow가 발생하게 된다.
    public void toString_양방향_순한_참조_문제() {
        final Member member = new Member();
        member.setEmail("asd@asd.com");
        member.setName("name");

        final Coupon coupon = new Coupon();
        coupon.setMember(member);

        final List<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon);
        member.setCouponList(coupons);

        System.out.println(member); // toString 순한 참조 발생
    }

    @Test
    public void EqualsAndHashCode_의_문제() {
    }

//    @Test
//    public void 클래스_상단의_Builder_의_문제() {
//
//        // id, createdAt, updatedAt은 데이터베이스에서 지정하기로 했는데 설정이 가능하다.
//        // email, name은 필수 값인데
//        Member.builder()
//            .id(1L)
//            .createAt(LocalDateTime.of(2021, 12, 12, 12, 12))
//            .updateAt(LocalDateTime.of(2010, 12, 12, 12, 12))
//            .build();
//
//        // 이미 사용한 쿠폰을 만들 수 있다.
//        Coupon.builder()
//            .used(false)
//            .build();
//    }
//
//    @Test
//    public void Builder_default_는_지양하자() {
//        final Member member = Member.builder()
//            .build();
//
//        then(member.getEmail()).isEqualTo("test@test.com"); // POJO 값 그대로 예상
//        then(member.getName()).isEqualTo("yun"); // POJO 값 그대로 예상
//    }
//
//    @Test
//    public void 생성자위_Builder_의_적적한_책임_부여() {
//        final Member member = Member.builder()
//            .name("asd")
//            .email("asd@asd.com")
//            .build();
//
////        Coupon.builder()
////            .member(member)
////            .build();
//
//    }
//
//    @Test
//    public void 생성자_접근_지시자는_최소한_으로() {
//        final Coupon coupon = new Coupon(); // 필수값, 비지니스로직을 모두 무시하고 객체 생성 가능
//        final Member member = new Member(); // 필수값, 비지니스로직을 모두 무시하고 객체 생성 가능
//    }
}
```

<br>



## @EqualsAndHashCode 의 남용

`@Equals` , `@HashCode` 코드는 `Member` 의 핵심필드인 id, email 에만 적용해도 충분하다. 하지만, `@EqualsAndHashCode` 가 `@Data` 를 통해 추가되면 모든 필드에 대해 equals(), hashCode() 메서드를 생성하게 된다.

아래 코드는 전체 필드들에 대해서 @EqualsAndHashCode 를 수행하고 있다.

실제로 코드를 열어보면 (`build/classes/java/main/io/study/jpa/builder_must_limit/entity/Member.class` ) 아래와 같이 equals hashcode 코드들이 모든 필드들에 대해 생성된 것을 볼 수 있다.

```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.study.jpa.builder_must_limit.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

public class Member {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
        name = "email",
        nullable = false,
        updatable = false,
        unique = true
    )
    private String email;
    @Column(
        name = "name",
        nullable = false
    )
    private String name;
    @OneToMany
    @JoinColumn(
        name = "coupon_id"
    )
    private List<Coupon> couponList = new ArrayList();
    @CreationTimestamp
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(
        name = "updated_at",
        nullable = false
    )
    private LocalDateTime updatedAt;

    public Member() {
    }

    public Long getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public List<Coupon> getCouponList() {
        return this.couponList;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCouponList(final List<Coupon> couponList) {
        this.couponList = couponList;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Member)) {
            return false;
        } else {
            Member other = (Member)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                Object this$email = this.getEmail();
                Object other$email = other.getEmail();
                if (this$email == null) {
                    if (other$email != null) {
                        return false;
                    }
                } else if (!this$email.equals(other$email)) {
                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if (this$name == null) {
                    if (other$name != null) {
                        return false;
                    }
                } else if (!this$name.equals(other$name)) {
                    return false;
                }

                label62: {
                    Object this$couponList = this.getCouponList();
                    Object other$couponList = other.getCouponList();
                    if (this$couponList == null) {
                        if (other$couponList == null) {
                            break label62;
                        }
                    } else if (this$couponList.equals(other$couponList)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$createdAt = this.getCreatedAt();
                    Object other$createdAt = other.getCreatedAt();
                    if (this$createdAt == null) {
                        if (other$createdAt == null) {
                            break label55;
                        }
                    } else if (this$createdAt.equals(other$createdAt)) {
                        break label55;
                    }

                    return false;
                }

                Object this$updatedAt = this.getUpdatedAt();
                Object other$updatedAt = other.getUpdatedAt();
                if (this$updatedAt == null) {
                    if (other$updatedAt != null) {
                        return false;
                    }
                } else if (!this$updatedAt.equals(other$updatedAt)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Member;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $couponList = this.getCouponList();
        result = result * 59 + ($couponList == null ? 43 : $couponList.hashCode());
        Object $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : $createdAt.hashCode());
        Object $updatedAt = this.getUpdatedAt();
        result = result * 59 + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    public String toString() {
        Long var10000 = this.getId();
        return "Member(id=" + var10000 + ", email=" + this.getEmail() + ", name=" + this.getName() + ", couponList=" + this.getCouponList() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }
}
```





