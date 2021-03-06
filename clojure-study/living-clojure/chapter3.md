# 상태, 동시성

`def`, `defn`로 정의하는 것들은 전역적이고 불변적인 성격

## 상태, 동시성 관리하기

동시성이 들어간 프로그래밍에서 상태 관리가 들어가면 크게 문제가 생기기 쉽다. Clojure는 이 상황에서 해결책이 될 수 있다.

## 독립적인 개체에는 Atom을 쓰자
Atom은 독립적인 개체의 상태를 담아둘 때 쓰인다. 이는 다른 부분의 상태의 변화와 무관하게 상태를 변화시킬 수 있다.

```clojure
(def who-atom (atom :catepillar))

who-atom
;=> 
```

* 아톰에서의 값의 변경은 항상 동기적이다.
* 값을 변경할때 `reset!`, `swap!` 을 사용한다.
  * 함수의 이름 끝에 느낌표를 붙이는 것은 함수가 상태를 바꾼다는 것을 표기하기 위해 사용한다. (일반적으로 트랜잭션)
* `swap!`은 첫번째 인자의 아톰을 두번째 인자로 오는 함수를 적용하여 결과값을 아톰에 적용한다.
  * 이 때, 두번째 인수로 오는 함수는 자동으로 Currying이 된다.
  * `swap!` 함수를 사용할때, side-effect가 없도록 하는게 중요하다.
    * 함수의 결과값을 아톰에 저장하기 전에, 현재의 아톰 값이 다른 스레드에 의해 변경되지 않았는지 주의해야 한다.
* `future` 형식은 body를 다른 스레드에서 실행한다.

dereferencing에는 @ 기호를 붙인다. 

## Ref

* STM(Software transaction memory)

Ref 특징
* Atomic
* Consistent
  * 트랜잭션을 완료하기 전에 값을 검사하는 검증 함수를 선택적으로 사용할 수 있다.
* isolated

`alter`형식은 아톰의 `swap`과 유사하게 동작한다.
* 단독으로 실행하면 에러를 발생시키기 때문에, `dosync`와 같이 사용한다.
* `dosync` 형식은 내부의 모든 상태변화를 조율하면서 트랜잭션을 처리한다.

`commute` 라는 함수는 트랜잭션 동안 재시도를 하지 않는다.
* 반드시 트랜잭션 안에서 호출해야 한다.
* 교환법칙이 성립하는 연산이다.

`ref-set`는 다른 값을 덮어쓸때 쓰인다.

## Agent

`send`로 에이전트의 상태를 바꿀 수 있다.
* 에이전트에 동작을 보내면 그 동작은 스레드 풀 중의 한 스레드에 의해 처리된다.
* 에이전트는 한 번에 하나의 동작만 처리하기 때문에 파이프라인 방식과 거의 비슷하다.
* 로그 파일을 쓰는 것과 같은 IO를 대기하는 작업에는 send는 적합하지 않다.

에이전트에 동작을 보내는 다른 방법으로 `send-off`가 있다.
* IO 대기가 있을 수 있는 동작에 사용해야 한다.


에이전트는 독립적으로 수행되는 작업에 아주 좋다. 다른 시스템으로 메시지를 중계하거나, 안전한 멀티스레드 방식으로 파일에 로그를 기록하거나 로봇을 제어하는 명령을 보내는 작업이 있을때 적합하다.


## 결론

* `Atom` : 동기적, 비조화
* `Ref` : 동기적, 조화
* `Agent` : 비동기적, 비조화

## 사족

* 왠만하면 `core.async` 를 많이 쓴다.