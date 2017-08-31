# Starting Out (for real)

## Numbers
모든 표현식(expression)은 온점(period, `.`)로 끝나며, 그 뒤에 여백이나 개행이 뒤따라오도록 해야한다.

표현식을 구분할때 쉼표(comma, `,`)를 이용할 수 있으나, 맨 마지막 표현식만 결과로 나타난다. (물론, 다른 표현식도 실행이 된다.)

Erlang의 문법은 Prolog의 영향을 받았다.

```erlang
1> 2 + 15.
17
2> 49 * 100.
4900
3> 1892 - 1472.
420
4> 5 / 2.
2.5
5> 5 div 2.
2
6> 5 rem 2.
1
```

나눗셈의 몫을 구할때는 `div`, 나머지를 구할때는 `rem`을 사용하자


```erlang
7> (50 * 100) - 4999.
1
8> -(50 * 100 - 4999).
-1
9> -50 * (100 - 4999).
244950
```

산술연산을 수행할때, 일반적인 연산자 우선순위를 따른다.

```erlang
10> 2#101010.
42
11> 8#0677.
447
12> 16#AE.
174
```

정수형 자료를 10진법이 아닌 다른 진법으로 나타내고 싶을때 `Base#Value`의 꼴로 나타낼 수 있다.

## Invariable Variables

```erlang
1> One.
* 1: variable 'One' is unbound
2> One = 1.
1
3> Un = Uno = One = 1.
1
4> Two = One + One.
2
5> Two = 2.       
2
6> Two = Two + 1.
** exception error: no match of right hand side value 3
7> two = 2.
** exception error: no match of right hand side value 2
```

* 함수형 프로그래밍에서 변수에 대해 값을 한번만 배정할 수 있다.
* 변수는 대문자로 시작한다.
* `=` 기호는 다음의 역할을 한다.
    * 매치 연산자 : 값이 일치할 경우 값을 반환하고, 일치하지 않을 경우 에러를 내뱉는 역할을 한다. (동등과 다름)
        * 패턴매칭에서 주로 쓰이는 연산이다. (튜플, 리스트를 이용)
    * 대입 연산자 : 오른쪽에 있는 값을 왼쪽에 있는 (바인딩 되어 있지 않은) 변수에 바인딩하는 역할을 한다.
* `_` 도 역시 변수이름에 쓰이지만, 매칭되는 변수를 고려할 필요가 없는 경우 관례적으로 쓰인다.

## Atoms
이름 자체로 고유한 값을 가지는 자료형이다. 변수가 소문자로 시작하지 못하는 이유가 여기에 있다. atom은 소문자로 이름이 시작한다.

```erlang
1> atom.
atom
2> atoms_rule.
atoms_rule
3> atoms_rule@erlang.
atoms_rule@erlang
4> 'Atoms can be cheated!'.
'Atoms can be cheated!'
5> atom = 'atom'.
atom
```

atom은 홑따옴표로 감싸서 나타낼 수도 있다.

패턴매칭을 이용할 때 유용하게 쓰이니, 능숙하게 이용하는 방법을 알아두는 것이 좋다.

### Don't drink too much Kool-Aid:
```
atom은 상수를 나타내거나 메시지를 전달할때 유용하게 쓰이긴 하지만, 항상 장점만 있는 것은 아니다.
atom은 atom table이라는 테이블을 참조하는데, 이는 각 atom마다 메모리를 잡아먹는다. (atom당 32비트 운영체제-4바이트, 64비트 운영체제-8바이트)
atom table은 가비지콜렉팅이 되지 않으며, 시스템이 뒤집어 엎어질때까지 atom은 계속 축적된다.

따라서 atom은 어떤 이유에서든지 동적으로 생성되서는 안되며, 개발자를 위한 도구로써 사용하는 것이 좋다.
```
* 참고 : 일부 atom은 예약어로서 사용되며, 언어 설계자가 의도한 용도 이외에는 사용할 수 없다. 
    * ex) 함수의 이름, 연산자, 표현식, etc. These are: after and andalso band begin bnot bor bsl bsr bxor case catch cond div end fun if let not of or orelse query receive rem try when xor


## Boolean Algebra & Comparison operators

true, false는 참/거짓을 의미하는 atom이다.

```erlang
1> true and false.
false
2> false or true.
true
3> true xor false.
true
4> not false.
true
5> not (true and true).
false
```

* short-circuit operator를 찾는다면 `andalso`, `orelse`를 이용할 것

### 비교 연산자 정리

* `=:=` : 동등한지 (타입까지 고려한 strict한 비교) 
    * `==` : 타입을 고려하지 않은 동등 연산자
* `=/=` : 동등하지 않은지(타입까지 고려한 strict한 비교)
    * `/=` : 타입을 고려하지 않음
* `<` : less than
* `>` : greater than
* `>=`
* `=<`

### 정렬순서
* `number < atom < reference < fun < port < pid < tuple < list < bit string`

## Tuples

