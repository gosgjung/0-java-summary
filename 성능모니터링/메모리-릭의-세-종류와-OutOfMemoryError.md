# 메모리 릭의 세 종류와 `OutOfMemoryError`



## 메모리 릭(Memory Leak) 이란?

쉽게 이야기하면, 어디선가 객체가 GC 되지 않아 메모리가 부족해지는 현상<br>

<br>

### `OutOfMemoryError` 이외의 다른 문제들

- 메모리 크래시
  - 메모리 크래시는 메모리 해제를 너무 빨리할 경우에 발생하는 문제를 의미한다.
  - 그리고 반드시 메모리 때문만은 아니다. 그 뒤의 내용은 조금 뒤에 정리 예정
- 너무 잦은 GC (너무 잦은 Full GC)
  - Minor GC가 자주 발생하는 것은 성능에 큰 영향을 주지는 않는다.
  - Full GC가 발생하는 것이 성능에 큰 영향을 준다.

<br>

### 잦은 GC가 자주 발생하지 않게끔 하려면?

- 임시 메모리의 사용을 최소화
- 객체의 재사용
- XML 처리시 메모리를 많이 점유하는 DOM 보다는 SAX를 사용
- 너무 많은 데이터를 한번에 보여주는 비즈니스 로직 제거
- 기타 프로파일링을 통해 임시 메모리를 많이 생성하는 부분 제거

<br>

## 메모리 릭의 세 종류

- 수평적 메모리 릭
- 수직적 메모리 릭
- 대각선 형태의 메모리 릭

<br>

### 수평적 메모리 릭

하나의 객체에서 매우 많은 객체를 참조하는 경우. 예를 들면 `ArrayList` 와 같은 리스트형태나 배열 형태에서 객체들을 계속 참조하고 놓아주지 않을 때 이러한 형태를 보인다.<br>

<br>

### 수직적 메모리 릭

각 객체들이 링크로 연결되어 있을 경우가 여기에 속한다. 가장 대표적인 것은 `LinkedList` 를 사용하는 경우다.<br>

<br>

### 대각선 형태의 메모리 릭

일반적으로 객체들이 복합적으로 메모리를 점유하고 있는 경우를 의미한다.<br>

<br>

## 세부 출처

이번 문서의 대부분은 [자바 트러블 슈팅](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791188621828&orderClick=LAG&Kc=) 의 내용을 참고했다.<br>

**Unveiling the java.lang.OutOfMemoryError**<br>

책에서 알려준 조금 오래된 글이지만, 미국의 황진우라는 분이 JDJ에 기고한 ‘Unveiling the java.lang.OutOfMemoryError(And dissecting java heap dumps) 라는 글에서는 메모리 릭을 세가지로 분류하고 있다. 하지만, 현재 자료를 찾아보기가 쉽지 않다..<br>

원본 문서 : [java.sys-con.com/node/1229281](http://java.sys-con.com/node/1229281)<br>

programmers sought : https://www.programmersought.net/article/329232676.html<br>

Webcast replay: Using IBM HeapAnalyzer to diagnose Java heap issues<br>

https://www.ibm.com/support/pages/webcast-replay-using-ibm-heapanalyzer-diagnose-java-heap-issues<br>

IBM HeapAnalyzer<br>

https://www.ibm.com/support/pages/ibm-heapanalyzer<br>

출처가 불명확하지만 남겨봄<br>

IBM의 Heap Analyzer 를 만든 분이 3가지 대표적인 OutOfMemory 의 징후들을 정리한 적이 있다고 한다. 오래된 글이기도 하고, 원본 글에는 프리젠테이션만 있어서, 일단 급한 상태로 모두 읽어볼 수 있는 상태도 아니어서 링크만 일단 남겨봤다.<br>

<br>

**What to do About Java Memory Leaks: Tools, Fixes, and More**<br>

[https://stackify.com/java-memory-leaks-solutions/](https://stackify.com/java-memory-leaks-solutions/ ) <br>

<br>