# 자바의 파일 클래스들

이번 문서는 자바의 파일 클래스의 기본적인 내용만 정리한다. 이번 문서에서는 기본적인 내용인 자바 7 이후로 도입된 `Path` 클래스와 기본적으로 제공되었던 `File` 클래스를 정리하고, 추후 다른 문서에서 바이너리 데이터 I/O 에 대한 내용, 다양한 종류의 파일 처리에 대해서 다룰 예정이다.<br>

자바 7 이후로 `java.nio.file` 패키지에 새로 추가된 `java.nio.file.Path` 클래스는 제공되는 기능이 유연하고 편리한 메서드 들이 많다. `java.nio.file.Path` 클래스는 자바 7 이전에 제공되던  `java.io.File` 클래스가 가진 단점들을 보완해서 새롭게 제공된 클래스다.<br>

`java.io.File` 클래스가 `java.nio.file.Path` 클래스와 비교했을 때 지원되지 않던 기능들은 아래와 같다.<br>

- 파일 메타 데이터(작성일, MIME 타입 등)를 취급하지 못한다.
- 심볼릭 링크를 처리하지 못했었다.
- 디렉터리 밑의 파일에 대해 생성/삭제/갱신을 감시하는 것이 불가능했었다.

이 외에도 자바의 `Path` 클래스, `Paths` 클래스를 활용하면 훨씬 유연한 파일 처리를 할 수 있다.<br>

<br>

## 자바의 `File` 클래스

자바에서는 기본적으로 `java.io.File` 클래스를 제공해준다.

- 파일의 존재 유무 확인
- 디레터리 내의 파일/디렉터리 리스팅
- 파일 read/write

 `File` 클래스를 사용하면 대표적으로 위의 3가지가 있다.<br>

자바의 `File` 클래스는 디렉터리 명 역시도 파일로 취급하는 것이 가능하다.<br>

<br>

**File 클래스는 절대경로, 상대경로 모두 지원된다.**<br>

```java
@Test
@DisplayName("File 클래스의 기초적인 사용법")
public void test1(){
    File file1 = new File("C:\\JAVA-MASTER\\test1.txt");
    String pathStr1 = String.valueOf(file1.getAbsolutePath());
    System.out.println(file1.exists());

    File file2 = new File("./inputDir/test1.txt");
    System.out.println(file2.exists());
}
```

<br>

출력결과

```plain
true
true
```

<br>

**다만, 구분자가 조금 불편하다.**<br>

`File` 클래스를 사용할 때, 디렉터리를 구분해주는 구분자(`Separator`)를 사용해야 하는데, 이 구분자는 윈도우에서는 `\` 을 사용하고 리눅스는 `/` 을 사용한다. 왜냐하면, 윈도우에서의 디렉터리 구별자가 `\` 이고, 리눅스에서의 디렉터리 구분자는 `/` 이기 때문이다.<br>

윈도우에서 사용하는 `\` 은 구별자로 사용하려면 `\\` 처럼 구분자를 하나 더 붙여서 사용해야 한다. 왜냐하면 `\` 은 이스케이프 문자이기 때문이다. 이런 이스케이프 문자를 일반 문자로 취급되게끔 하기 위해 `\` 을 하나 더 붙여서 `\\` 을 구분자로 만들어준다.<br>

```java
@Test
@DisplayName("윈도우에서는 파일 구분자가 다르다. \\ 이다.")
public void test2(){
    File file1 = new File("C\\JAVA-MASTER\test1.txt");
    System.out.println(file1.exists());

    File file2 = new File("C:\\JAVA-MASTER\\test1.txt");
    System.out.println(file2.exists());
    System.out.println("문자열 내에서 \\ 을 사용하려면 '\\\\' 으로 문자열을 만들어야 합니다.");
}
```

<br>

출력결과

```plain
false
true
문자열 내에서 \ 을 사용하려면 '\\' 으로 문자열을 만들어야 합니다.
```

<br>

**OS 별로 다른 구분자를 갖도록 지정해보자.**<br>

이런 이유로 애플리케이션을 배포할 때 사용자의 OS가 리눅스인지, 윈도우계열인지에 따라서 디렉터리의 구분자를 다르게 하게끔 세팅하는 것이 필요하다. 또는 개발 환경을 리눅스를 사용하는 경우도 있고, 맥OS를 사용하는 경우도 있고, 윈도우를 사용하는 경우도 있기에 개발 환경별로 유연하게 구분자가 적용될 수 있는 방법이 필요하다.<br>

```java
@Test
@DisplayName("File Separator 를 사용해보기")
public void test3(){
    System.out.println("현재 사용하고 있는 운영체제의 디렉터리 구분자는 " + File.separator + " 입니다.");
}
```

<br>

출력결과

```plain
현재 사용하고 있는 운영체제의 디렉터리 구분자는 \ 입니다.
```

<br>

**`File` 클래스 내에는 파일이 아닌 디렉터리도 지정할 수 있다.**<br>

자바의 `File` 클래스는 디렉터리 명 역시도 파일로 취급하는 것이 가능하다.<br>

```java
@Test
@DisplayName("File 클래스로 디렉터리의 하위 디렉터리를 출력해보자")
public void test5(){
    File file1 = new File("C:\\JAVA-MASTER");

    System.out.println("C:\\JAVA-MASTER 디렉터리 아래의 모든 디렉터리를 출력해봅니다. >> ");
    for(String file : file1.list()){
        System.out.println(file);
    }
    System.out.println();

    System.out.println("현재 프로젝트의 루트 디렉터리 아래의 모든 파일 객체를 출력해봅니다 >> ");
    File file2 = new File("."); // 현재 프로젝트 디렉터리의 모든 파일 객체를 출력해보자.
    for(String file : file2.list()){
        System.out.println(file);
    }
    System.out.println();
}
```

<br>

출력결과

```plain
C:\JAVA-MASTER 디렉터리 아래의 모든 디렉터리를 출력해봅니다. >> 
test1.txt

현재 프로젝트의 루트 디렉터리 아래의 모든 파일 객체를 출력해봅니다 >> 
.gitignore
.idea
.mvn
HELP.md
inputDir
mvnw
mvnw.cmd
pom.xml
src
target
```

<br>

## 자바의 `Path` 클래스

자바 7 이후부터 `java.nio.file.Path` 클래스가 새로 제공되었다. `java.nio.file.Path` 클래스는 자바 7 이전에 제공되던  `java.io.File` 클래스가 가진 단점들을 보완해서 새롭게 제공된 클래스다.<br>

`java.nio.file.Path` 클래스는 제공되는 기능이 유연하고 편리한 메서드 들이 많다. <br>

`java.io.File` 클래스가 `java.nio.file.Path` 클래스와 비교했을 때 지원되지 않던 기능들은 아래와 같다.<br>

- 파일 메타 데이터(작성일, MIME 타입 등)를 취급하지 못한다.
- 심볼릭 링크를 처리하지 못했었다.
- 디렉터리 밑의 파일에 대해 생성/삭제/갱신을 감시하는 것이 불가능했었다.

이 외에도 자바의 `Path` 클래스, `Paths` 클래스를 활용하면 훨씬 유연한 파일 처리를 할 수 있다. 그리고 위의 문제점 들을 보완해서 제공하는 메서드들을 갖추고 있다. 또한 기존에 사용하던 `File` 클래스를 `Path` 클래스 내에서 혼합해서 사용하는 것 역시 가능하다.<br>

<br>

**기본적인 예제, 파일 구분자 없이 파일 및 디렉터리에 접근가능하다.**<br>

```java
@Test
@DisplayName("Paths 클래스를 이용한 디렉터리 지정")
public void test6(){
    // 절대경로를 파일 구분자 없이 지정하기(윈도우에서만 동작한다 ㅠㅠ)
    // 디렉터리 구분자 없이 지정하는 것은 확실히 유연하고 편리하다.
    Path path1 = Paths.get("C:", "JAVA-MASTER", "test1.txt");
    assertThat(Files.exists(path1)).isTrue();

    // 절대 경로도 Paths 클래스로 지정하는 것 역시 가능하다.(윈도우에서만 동작한다.)
    Path path2 = Paths.get("C:\\JAVA-MASTER\\test1.txt");
    assertThat(Files.exists(path2)).isTrue();

    // C: 가 아닌 C 로 드라이브를 지정하면 디렉터리를 못찾는다.
    Path path3 = Paths.get("C", "JAVA-MASTER", "test1.txt");
    assertThat(Files.exists(path3)).isFalse();

    // 윈도우의 파일 구분자인 \ 을 쓰지 않아도 Path 클래스에서는 /(슬래시)를 사용하고도 Path 를 지정하는 것이 가능하다.
    Path path4 = Paths.get("C:/JAVA-MASTER/test1.txt");
    assertThat(Files.exists(path4)).isTrue();

    // 상대 경로를 파일 구분자 없이 지정하기
    // 디렉터리 구분자 없이 지정하는 것은 확실히 유연하고 편리하다.
    Path path5 = Paths.get("inputDir", "test1.txt");
    assertThat(Files.exists(path5)).isTrue();

    // URI 형식 역시도 Path 클래스로 지정가능하다.
    URI uri = URI.create("file:///C:/JAVA-MASTER/test1.txt");
    Path path6 = Paths.get(uri);
    assertThat(Files.exists(path6)).isTrue();
}
```

<br>

**부모 디렉터리, 디렉터리 아래의 파일/디렉터리에 접근**<br>

```java
@Test
@DisplayName("디렉터리, 디렉터리 내의 모든 파일들 얻어오기")
public void test7(){
    Path path1 = Paths.get("inputdir", "test1.txt");
    System.out.println("path1.getParent() = " + path1.getParent());
    assertThat(Files.exists(path1)).isTrue();

    Path path2 = path1.resolveSibling("test2.txt");
    System.out.println("path2 = " + path2);
    assertThat(Files.exists(path2)).isTrue();

    Path path3 = path1.resolveSibling("../README1.md");
    System.out.println("path3.toString() = " + path3.toString());
    System.out.println("path3.normalize().toString() = " + path3.normalize());
    assertThat(Files.exists(path3)).isTrue();
}
```

<br>

출력결과

```plain
path1.getParent() = inputdir
path2 = inputdir\test2.txt
path3.toString() = inputdir\..\README1.md
path3.normalize().toString() = README1.md
```

<br>

**`Path` 클래스는 다른 객체로도 변환할 수 있다.**<br>

```java
@Test
@DisplayName("Path 객체를 File 객체 또는 URI 객체로 변환해보기")
public void test8(){
    Path path = Paths.get("inputDir", "test1.txt");
    File file = path.toFile();
    System.out.println(file);

    URI uri = path.toUri();
    System.out.println(uri);
}
```

<br>


