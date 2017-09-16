# Module

## What are modules

모듈은 하나의 파일에 여러개의 함수들을 묶어서 하나의 이름으로 그룹화시킨 것. 내장함수 `hd`, `tl`도 역시 `erlang` 모듈에 들어가있다. `erlang` 모듈의 내장함수는 다른 함수와는 다르게 디폴트로 import 된다. 모듈안에 정의되는 다른 모든 함수는 `Module:Function(Arguments)` 같은 형태로 불러와야 한다.

```erlang
1> erlang:element(2, {a,b,c}).
b
2> element(2, {a,b,c}).
b
3> lists:seq(1,4).
[1,2,3,4]
4> seq(1,4).
** exception error: undefined shell command seq/2
```

`erlang` 모듈에서 자동으로 import 되지 않는 함수도 있긴 하지만, 그리 자주 쓰이지도 않는다.

리스트를 이용하는 연산은 주로 `lists` 모듈에 정의되어 있다.

입출력을 수행하는 모듈은 `io` 모듈에 정의되어 있다.

물론 `erlang` 모듈 같은 경우 멀티프로세싱, 변환, 수학, 가상머신 세팅 등 위의 사례와 일관되지 않게 모듈이 구성되어 있으나, 왠만하면 논리적으로 분리되어 있도록 모듈을 작성해야 한다.

## 모듈 선언하기

모듈을 작성할때 두가지 속성을 선언해야 한다. functions, attributes

attribute는 모듈의 이름, 함수의 가시성, 코드의 작성자 등에 대한 정보를 나타내는 메타데이터이다. 이러한 메타데이터는 컴파일러에게 어떤 작업을 실행해야 하는지, 다른 사람들이 코드를 보지 않고서도 컴파일된 코드를 이용해서 쓸만한 정보를 얻어낼 수 있도록 도와주기 때문에 중요하다.

몇가지 미리 정의된 attribute들을 알아보도록 하자.

### -module(Name).
현재 모듈의 이름을 알려주기 때문에, 파일의 맨 처음에 명시해두는 것이 좋다. 여기서 Name은 atom 이 들어가야 한다. 이는 다른 모듈에서 함수를 호출할때 쓰이는 이름이 되기도 한다. 이러한 호출은 `Module:Function(Arguments)`의 형태를 보인다는 것을 보였다.

텍스트에디터로 편집하고, 아래의 코드를 타이핑하고 `useless.erl` 이라는 이름으로 저장하면 된다.
```erlang
-module(useless)
```

### -export([Function1/Arity, Function2/Arity, ..., FunctionN/Arity]).
바깥에서 어떤 함수를 호출할 수 있는지 정의할 수 있다. `함수/애리티` 의 리스트를 받는다. 함수의 애리티는 인자가 얼마나 함수에 전달되는지를 숫자로 나타낸 값이다. 같은 모듈안에서 정의된 다른 함수가 다른 애리티를 가진다면 같은 이름을 공유할 수도 있기 때문에, 이는 중요한 정보라고 할 수 있다. `add(X,Y)` 와 `add(X,Y,Z)` 는 다르다고 생각할 수 있으며, 각각 `add/2`, `add/3`로 나타낼 수 있다.

Export 된 함수는 모듈의 인터페이스를 나타낸다.

모듈을 선언하고 난 뒤 다음과 같이 `export` attribute를 추가하자.

```erlang
-export([add/2])
```

다음과 같이 함수를 작성하자.
```erlang
add(A,B) ->
    A + B.
```

함수의 문법은 `Name(Args) -> Body` 을 따른다. 여기서 Name은 atom이고, Body는 콤마로 구분된 하나 이상의 표현식이다. 함수는 반드시 온점으로 끝난다.

Erlang은 return 키워드가 없다는 점에 주의하자. Return 이 없는 대신, 마지막에 실행되는 표현식이 나타내는 값을 반환하게 된다.

한줄 주석을 작성할때는 `%` 를 이용한다.(코딩스타일에 따라 `%%`를 쓰기도 한다.)

### -import(Module, [Function1/Arity, ..., FunctionN/Arity]).
특정 모듈에서 함수를 import 할 때 사용한다.

### -define(MACRO, some_value).
C의 `#define` 같은 역할을 한다.

불러올때는 `?MACRO` 로 불러온다.

예를 들어 `-define(sub(X,Y), X-Y)`라 정의했을 경우, `?sub(23,47)` 의 경우 컴파일러를 거쳐 `23-47`로 치환된다.

## Compiling the code
Erlang의 코드는 가상머신에 올라가기 위해 바이트코드로 컴파일 된다. 컴파일러를 불러올때 커맨드라인에서는 `erlc` 명령, 쉘이나 모듈 내에서는 `compile:file(FileName)`, 쉘에서는 `c()` 로 불러온다.

Erlang의 함수 및 표현식은 어떻게든 값을 반환한다. `io` 모듈의 입출력도 정상적으로 이루어졌다는 것을 나타내기 위해 `{:ok}`를 반환한다.

다음은 컴파일 할 때 자주 지정하게 되는 플래그들이다.
* -debug_info
* -{outdir,Dir}
* -export_all
* {d,Macro} or {d,Macro,Value}

## More About Modules
`useless:module_info/0` 를 통해 모듈의 메타데이터를 뽑아낼 수 있다.
```erlang
9> useless:module_info().
[{exports,[{add,2},
{hello,0},
{greet_and_add_two,1},
{module_info,0},
{module_info,1}]},
{imports,[]},
{attributes,[{vsn,[174839656007867314473085021121413256129]}]},
{compile,[{options,[]},
{version,"4.6.2"},
{time,{2009,9,9,22,15,50}},
{source,"/home/ferd/learn-you-some-erlang/useless.erl"}]}]
10> useless:module_info(attributes).
[{vsn,[174839656007867314473085021121413256129]}]
```

`module_info/1` 명령은 그 정보의 일부분을 추려낼때 쓴다.

정의된 함수의 리스트를 쭉 살펴보고, 주석이 달려 있는 함수를 찾는 쪽으로 응용할 수도 있다.

