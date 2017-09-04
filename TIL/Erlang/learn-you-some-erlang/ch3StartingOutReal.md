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
* 튜플은 정해진 순서, 정해진 길이로 데이터를 한 군데 묶기 위한 방법이다.
    * ex) 좌표, 레코드, ...

```erlang
1> X = 10, Y = 4.
4
2> Point = {X,Y}.
{10,4}
```

적절히 순서만 주어진다면, 튜플에서 가져가고자 하는 자료만 빼낼 수도 있다.
* 매치연산자를 이용할 때, 튜플의 길이도 역시 일치해야함을 명심하자.
```erlang
3> Point = {4,5}.
{4,5}
4> {X,Y} = Point.
{4,5}
5> X.
4
6> {X,_} = Point.
{4,5}
7> {_,_} = {4,5}.
{4,5}
8> {_,_} = {4,5,6}.
** exception error: no match of right hand side value {4,5,6}
```

erlang에서 _ 는 신경쓰지 않아도 되는 모든 값을 의미하는 와일드카드 정도로 생각하면 된다.

```erlang
10> PreciseTemperature = {celsius, 23.213}.
{celsius,23.213}
11> {kelvin, T} = PreciseTemperature.
** exception error: no match of right hand side value {celsius,23.213}
```

튜플은 매치 연선자를 이용하여, 의도하지 않은 에러를 잡아낼때 유용하다.

atom 이 들어간 튜플을 tagged tuple이라고 칭하기도 한다.

튜플에 튜플이 들어갈 수도 있다.
```erlang
12> {point, {X,Y}}.
{point,{4,5}}
```

## Lists!

리스트는 함수형 프로그래밍 언어에서 주로 쓰이는 자료형 중 하나이기도 하다.
리스트의 길이는 가변적이며, 앞에서 append하거나, 머리부분과 꼬리부분을 따로 떼네는 등의 연산이 주로 이루어진다.

리스트는 어떤 값이든 들어갈 수 있다.

```erlang
1> [1, 2, 3, {numbers,[4,5,6]}, 5.34, atom].
[1,2,3,{numbers,[4,5,6]},5.34,atom]
```

문자열과 리스트를 나타내는 표기법이 같은 점에 유의하자.

```erlang
2> [97, 98, 99].
"abc"
3> [97,98,99,4,5,6].
[97,98,99,4,5,6]
4> [233].
"é"
```

숫자로 이루어진 리스트의 한 요소가 문자로 나타낼 수 없는 경우에는 숫자로만 나타내는 점에 유의하자.


리스트를 이어붙일 때는 `++`를 사용한다. 반대역할을 하는 `--`도 있다.

```erlang
5> [1,2,3] ++ [4,5].
[1,2,3,4,5]
6> [1,2,3,4,5] -- [1,2,3].
[4,5]
7> [2,4,2] -- [2,4].
[2]
8> [2,4,2] -- [2,4,2].
[]
```

`++`와 `--`는 right-associative 니 주의할 것

```erlang
9> [1,2,3] -- [1,2] -- [3].
[3]
10> [1,2,3] -- [1,2] -- [2].
[2,3]
```

리스트 앞부분과 나머지 부분을 뽑아내는 내장 함수도 있다.

```erlang
11> hd([1,2,3,4]).
1
12> tl([1,2,3,4]).
[2,3,4]
```

패턴매칭을 이용해, 리스트로부터 앞부분을 떼어내는 방법도 있다. 먼저, 리스트에 새로운 앞부분을 추가하는 방법부터 보자.
그 경우에는 `[Head|Tail]` 표기법을 사용한다. 

```erlang
13> List = [2,3,4].
[2,3,4]
14> NewList = [1|List].
[1,2,3,4]
```

리스트를 처리할 때, 앞부분과 나머지부분을 따로 떼어내서 어딘가에 저장하는 방식을 동시에 하고 싶을때가 있는데, 튜플에서 패턴매칭을 써먹던 방식 그대로 쓸 수 있다. 

`|` 는 리스프의 `cons` 같은 역할을 한다. 리스트는 오직 cons와 값으로만 만들어 질 수 있다.

```erlang
20> [1 | []].
[1]
21> [2 | [1 | []]].
[2,1]
22> [3 | [2 | [1 | []] ] ].
[3,2,1]
```

다음은 같은 리스트를 나타낸다.
```
[a, b, c, d]
[a, b, c, d | []]
[a, b | [c, d]]
[a, b | [c | [d]]]
[a | [b | [c | [d]]]]
[a | [b | [c | [d | [] ]]]]
```

위를 이해하고 나면 리스트컴프리헨션도 어렵지 않게 이해할 수 이싿.

### Don't drink too much Kool-Aid

Erlang은 애초에 통신사에서 개발된 언어였고, 그러한 목적으로 개발되었기 때문에 문자열 모듈 지원이 빈역했다. 요즘 들어서 VM에서 Unicode 문자를 지원하고, 문자열 처리가 빨라졌으니 상관없음.


문자열을 이진자료구조로서 취급할 수도 있다.


## List Comprehensions

리스트 컴프리헨션은 리스트를 만들거나 조작하는 방법이다. 프로그램을 짧게 만들고, 리스트를 조작하는 다른 방법보다 이해하기 쉽다. 집합에서의 조건제시법에서 비롯된 아이디어이다. 조건제시법은 집합을 어떻게 생성할 지 각 요소가 만족시켜야 하는 조건을 명시하며 알려준다.

L이 리스트 [1,2,3,4] 일 때, 집합 `{2n : n in L}`는 Erlang으로 다음과 같이 나타낼 수 있다.
```erlang
1> [2*N || N <- [1,2,3,4]].
[2,4,6,8]
```

리스트 컴프리헨션에서 불리언값을 반환하는 연산을 이용해서 제약조건을 추가할 수 있다. 만약 1부터 10까지의 숫자 중에서 짝수인 수를 뽑아내고자 한다면 다음과 같이 쓸 수 있다.
```erlang
2> [X || X <- [1,2,3,4,5,6,7,8,9,10], X rem 2 =:= 0].
[2,4,6,8,10]
```

다음과 같이 복잡한 예시에도 사용할 수 있다.
```erlang
3> RestaurantMenu = [{steak, 5.99}, {beer, 3.99}, {poutine, 3.50}, {kitten, 20.99}, {water, 0.00}].
[{steak,5.99},
{beer,3.99},
{poutine,3.5},
{kitten,20.99},
{water,0.0}]
4> [{Item, Price*1.07} || {Item, Price} <- RestaurantMenu, Price >= 3, Price =< 10].
[{steak,6.409300000000001},{beer,4.2693},{poutine,3.745}]
```

Erlang의 리스트 컴프리헨션은 다음과 같이 나타낼 수 있다고 생각하면 된다.
`NewList = [Expression || Pattern <- List, Condition1, Condition2, ... ConditionN]`

`Pattern <- List` 는 제네레이터 표현식이라 불린다. 제네레이터 표현식은 두개 이상을 가질 수도 있다.

```erlang
5> [X+Y || X <- [1,2], Y <- [2,3]].
[3,4,4,5]
```

위의 공식에 좀 더 보태면 다음과 같이 나타낼 수 있다. `NewList = [Expression || GeneratorExp1, GeneratorExp2, ..., GeneratorExpN, Condition1, Condition2, ... ConditionM]`

제네레이터 표현식은 패턴매칭과 맞물려서 쓰일 수도 있다.

```erlang
6> Weather = [{toronto, rain}, {montreal, storms}, {london, fog},  
6>            {paris, sun}, {boston, fog}, {vancouver, snow}].
[{toronto,rain},
{montreal,storms},
{london,fog},
{paris,sun},
{boston,fog},
{vancouver,snow}]
7> FoggyPlaces = [X || {X, fog} <- Weather].
[london,boston]
```

----------- 아직까지 쓰일일이 없어서 유도리있게 스킵~ But 주목해볼 만한 특성인 것 같음.

## Bit Syntax!
Erlang에서 바이너리 데이터를 패턴매칭할 수 있다. 비트 단위로 조작하기 때문에, 통신모듈에서 유용하게 쓰인다. 

## Binary Comprehension

```erlang
1> [ X || <<X>> <= <<1,2,3,4,5>>, X rem 2 == 0].    
[2,4]
2> Pixels = <<213,45,132,64,76,32,76,0,0,234,32,15>>.
<<213,45,132,64,76,32,76,0,0,234,32,15>>
3> RGB = [ {R,G,B} || <<R:8,G:8,B:8>> <= Pixels ].
[{213,45,132},{64,76,32},{76,0,0},{234,32,15}]
```