# 자바 기초 문법 정리

프로그래밍을 C#으로 처음 접한 EXP 동아리 회원들이 알고리즘 스터디하고자 할 때, Java를 이용해서 스터디할 경우를 대비해서 정리했습니다. C/C++ 배우다가 Java 쓰려는 사람에게는 다소 불친절한 문서가 될 수도 있으니 참고바랍니다.

## Primitive type (C# VS Java)

|C#|Java|
|--|:-:|
|bool|boolean|
|short|short|
|int|int|
|float|float|
|long|long|
|double|double|
|string|String|

## flow control

C#이랑 Java랑 별차이 없음

### If
```Java
if (condition) {
    // true statement
} else {
    // false statement
}
```

### For loop
```java
for(init; condition; inc/dec) {
    // statements;
}
```

### While loop
```java
while(condition) {
    // statements;
}
```

## Array
다차원 배열을 생성할 때, 차이가 나므로 주의바람

#### 크기 10짜리 int형 배열 만들기
```java
int[] a = new int[10];
```
#### 4x3 2차원 배열 만들기
```java
// java
int[][] = new int[4][3];
```

```csharp
// C#
int[,] = new int[4,3]
```
#### Problem Solving 할 때 참고

왠만하면 배열은 초기화할 때, 0으로 채워짐.
```
For type byte, the default value is zero, that is, the value of (byte) is 0.
For type short, the default value is zero, that is, the value of (short) is 0.
For type int, the default value is zero, that is, 0.
For type long, the default value is zero, that is, 0L.
For type float, the default value is positive zero, that is, 0.0f.
For type double, the default value is positive zero, that is, 0.0d.
For type char, the default value is the null character, that is, '\u0000'.
For type boolean, the default value is false.
For all reference types, the default value is null.
```

## IO

```java
```

## 클래스, 접근제어, ...

#### 상수

```java
final
```

```C#
const
```

## 컬렉션 이용하기

```java
// java
import java.util.*;

ArrayList
Queue
Stack
HashMap

```

```csharp
// C#
using System.Collection.Generics;

Stack
List 
Queue
Dictionary
```

## 주 참고자료
* http://introcs.cs.princeton.edu/java/lectures/