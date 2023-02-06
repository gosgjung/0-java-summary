### static 키워드

쉽다면 쉽고, 귀찮다고 생각하면 귀찮은 static 키워드.

머릿속에서 상상하는 것과 한번 쯤은 문서로 정리해두는 것과 다르다고 생각하기에 정리를 시작.

<br>



### static 이란 ?? 

static 은 정적인 변수이고, 컴파일 타임에 값이 결정되는 변수.<br>

주로 공통으로 사용되는 값이나 한번 정의 된 후 변하지 않는 값들에 대해 적용을 검토하는 편.<br>

static 은 객체에 소속된 멤버가 아니라 클래스에 고정된 멤버다.<br>

클래스 로더가 클래스를 로딩해서 메소드 메모리 영역에 적재시, 클래스 별로 관리된다. 즉, 클래스 로딩이 끝나는 즉시 바로 사용할 수 있다.<br>

<br>



### JVM 에서의 static 변수/메서드의 위치

JVM 에서는 Java 11 부터였던가 이때부터는 힙에 저장되게 되었다고 한다. 이거 오늘 오후에 출처와 확실한 자료를 통해 정리 예정 (시간이 부족...)

<br>



### 참고) 클래스 멤버 변수 초기화 순서

1\) static 변수 선언부

- 클래스 로드 시 static 필드 선언부가 가장 먼저 초기화 된다.

2\) 필드 변수 선언부

- 객체가 생성시 생성자 영역 보다 먼저 초기화 된다.

3\) 생성자 영역

- 객체 생성시 JVM이 내부적으로 생성자 영역을 locking 한다. (thread safe 영역)

<br>



### static 을 사용하는 이유

런타임에 결정되는 값이 아니라 컴파일 타임에 값이 결정되어 정적인 값을 제공해야 하는 경우이거나,<br>

프로그램 로딩 후 프로그램 종료 시 까지 계속해서 메모리에 상주해야 하는 경우 static으로 선언한다.<br>

매번 메모리에 값을 로딩하기 보다는, 정적으로 값을 제공해서 일종의 전역변수 처럼 사용해야 할 경우에 `static` 키워드를 사용한다.<br>

<br>

인스턴스로 생성하지 않고도 바로 사용가능하기 때문에 프로그램 전역적으로 공통으로 사용해야 하는 데이터들을 관리할 때 자주 사용되는 편이다.<br>

<br>

우리가 자주 사용하는 스프링 프레임워크의 `MediaType` 역시 내부 구현을 보면 `static final` 로 선언된 필드들이 많다. 직관적으로 생각했을 때 보통은 Enum 으로 구현되었을 것이라는 생각도 들때가 있지만, 막상 내부 구현을 보면 static 블록과 static 멤버필드들로 구현되어 있다.<br>

<br>



### e.g. static 멤버필드, static 멤버 메서드

static 멤버 필드는 객체를 생성하지 않고도 클래스 내의 변수에 접근이 가능한 변수다. 객체 레벨이 아닌 클래스 레벨에서 멤버 필드에 접근할 수 있다.<br>

static 멤버 메서드는 객체를 생성하지 않고도 클래스 내부의 메서드에 접근이 가능한 메서드다. 객체 레벨이 아닌 클래스 레벨에서 멤버 필드에 접근할 수 있다.<br>

<br>



#### static 멤버 필드

```java
class Number{
    static int num = 0;
    int num2 = 0;
}

public class StaticEx{
    public static void main(String [] args){
        Number n1 = new Number();
        Number n2 = new Number();
        
        n1.num++;		// static 변수 ++
        n1.num2++;		// 객체 변수 ++
        
        System.out.println(n2.num); 
        System.out.println(n2.num2);
    }
}
```



출력결과

```plain
1
0
```

<br>



static 변수는 `n1.num++` 에서 증가시켜놓은 값 그대로 가지고 있고, 일반 객체 변수는 객체에서 자기만의 스코프를 가지기 때문에 `n1.num2++` 에서 증가시킨 값은 `n2.num2` 로 못불러온다.

<br>



#### static 멤버 메서드

```java
class Hello {
    static void engHello(){
        System.out.println("hello~");
    }
    
    void korHello(){
        System.out.println("헬로");
    }
}

public class StaticTest{
    public static void main (String [] args){
        Hello.engHello();			// 1) static 메서드 호출
        new Hello().korHello();		// 2) 객체의 메서드 호출
    }
}
```

<br>



`1 )`  

- static 메서드를 호출하고 있다.
- Hello 클래스의 static 메서드인 `engHello()` 메서드를 호출하고 있다.

<br>

`2 )` 

- Hello 클래스의 객체를 생성해서 `korHello()` 메서드를 호출하고 있다.

<br>



