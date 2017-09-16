# Syntax in functions

## Pattern Matching

함수에도 패턴매칭을 적용할 수 있다.
패턴매칭을 이용하는 함수절끼리 구분을 하기 위해, `;` 기호를 쓴다.


```erlang
function(X) ->
    Expression;
function(Y) ->
    Expression;
function(_) ->
    Expression.
```

`참고` : `io:format`의 포맷팅은 문자열로 변환되는 몇가지 토큰의 도움을 받는다. 이러한 토큰은 `~`로 시작한다. 예를 들어, `~n`는 개행으로 바뀐다. `~s` 가 포함된 함수호출 `io:format("~s!~n", ["Hello"]).`의 경우 문자열이나 비트문자열을 인자로 받으며, 최종적인 출력은 `Hello!\n`가 될 것이다. 그 외에도 널리 쓰이는 토큰은 `~p`인데, 들여쓰기를 하는 등등 보기 좋게 출력해주는 역할을 한다.

`erlang:hd/1`를 구현하는 함수 `head/1`를 짜보자
```erlang
head([H|_]) -> H.
```

리스트의 두번째 요소를 추출하는 `second/1`도 짜보자
```erlang
second([_,X|_]) -> X.

1> c(functions).
{ok, functions}
2> functions:head([1,2,3,4]).
1
3> functions:second([1,2,3,4]).
2
```

두 인자가 같은지 나타내는 `same/2` 함수도 짤 수 있다.
```erlang
same(X,X) ->
  true;
same(_,_) ->
  false.
```