# jmap 사용법

**참고자료**<br>

[https://hbase.tistory.com/180](https://hbase.tistory.com/180)<br>

<br>

**jmap 이란?**<br>

가장 직관적이면서도, 가장 빠르게 확인할 수 있는 방식<br>

그라파나 등을 이용하는 것은 평상시 운영시에 확인하는 용도로 사용하고,<br>

빠르게 뭔가를 개발 시에 확인해보고 싶을 때는 덤프를 떠서 확인하는 것이 가장 좋은 방식이다.<br>

만약 개발시에 느긋하게 양반처럼 그라파나를 보고 하면, 문제 파악이 힘들어진다.<br>

<br>

**사용법**<br>

프로세스 아이디 조사<br>

- 프로세스 아이디를 따로 기억해두거나 클립보드에 복사해둔다.

```bash
ps -ef | grep java
```

<br>

메모리 덤프 뜨기<br>

```bash
jmap -histo:live 169843 > memory-dump1.txt
```

<br>

