# 제어문과 함수형 반환

## 용어 정리

* 구조 분해(destructuring)
  * 컬렉션의 요소 중에 관심의 대상인 요소를 뽑아내어 이름을 부여하는 방법
* 지연 평가(lazy evaluation)
  * 재귀(recursion)

#### 식(expression), 형식(forms)

* 식 : 평가될 수 있는 코드
* 형식 : 평가될 수 있는 적법한 식
  * 문법에 맞지 않으면 형식이 아니다.


## 흐름 제어(Control flow)


### 기본적인 논리 검사

* `class`라는 함수를 통해, 평가된 값이 어떤 클래스에 속하는지 확인할 수 있다.
  * 예시 : `(class true) ;=> java.lang.Boolean`
* 함수의 값이 불리언 값을 경우, 함수의 이름 맨 끝에 물음표를 붙이는 것이 관례이다. (ruby와 비슷함)
  * 예시 : `(true? true) ;=> true`, `(false? false) ;=> true`
  * 값이 없는지 확인할 때는 `nil?` 함수를 이용할 수 있다.

#### 논리 검사 목적으로 쓰이는 함수 정리
  * `(true? a)` : 참인지 검사한다.
  * `(false? a)` : 거짓인지 검사한다.
  * `(nil? a)` : 값이 없는지 검사한다.
  * `(= a b)` : 동등임을 검사한다.
    * `(not= a b)` : `(not (= a b))` 의 축약형, 동등이 아님을 검사한다.

### 컬렉션에 사용하는 논리 검사

#### 컬렉션 추상 / 시퀀스 추상
* 컬렉션은 벡터, 리스트, 맵 처럼 단순히 요소를 모아놓은 것.
  * 컬렉션은 `clojure.lang.IPersistentCollection`(컬렉션 추상) 인터페이스를 구현한 존속적, 불변적인 자료구조이다.
  * 이를 통해 컬렉션들은 `count`, `conj`, `seq` 같은 함수들을 공유한다.
* `seq` 함수는 컬렉션을 받아서 시퀀스로 바꾸어주며, 빈 컬렉션이면 `nil`을 반환한다.
  * 시퀀스는 시퀀스 추상을 구현한 것이다.
  * 시퀀스 추상은 컬렉션을 리스트처럼 순차적으로 다룰 수 있게 해준다.
  * 시퀀스는 존속적이고 불변이며 `first`, `rest`, `cons` 함수를 구현한다.
* 실제로, 컬렉션을 처리하는 많은 함수들이 내부적으로 `seq` 함수를 사용하고 있다.
  * 예를 들어, `first`를 벡터에 대해서 적용할 경우, 벡터를 시퀀스로 바꿔주어야 하는데, 이 때, 내부적으로 사용자 대신 `seq` 함수를 사용한다.

#### 컬렉션에 논리 검사를 적용하기 위해 쓰이는 함수 정리

* `(empty? a)` : 컬렉션이 비어있는지 확인
  * 실제 정의 : `(defn empty? [col] (not (seq coll)))`
* `(every? fn coll)` : 진위 함수가 컬렉션의 모든 요소에 대해 참으로 평가하면 `true`, 아니면 `false`를 반환
* `(not-any? fn coll)` : 컬렉션의 요소가 하나라도 참이면 `false`를 반환
* `(some fn coll)` : 진위 함수가 평가한 값이 처음으로 논리적 참일 때 그 평가한 값을 반환하고, 아니면 `nil`을 반환한다.
    * 시퀀스에 요소가 들어 있는지 확인할 때, 집합을 진위함수로 사용하면 매우 편리
    * 집합을 진위함수로 사용할 경우, 집합에 있는 요소와 처음으로 일치하는 시퀀스 요소를 반환 
        * 예시 : `(some #{4 5} [1 2 3 4 5]) ;=> 4`

### 흐름 제어 이용하기

* `if` 문의 기본 구성
    ```clojure
    (if boolean-function        ;;  진위 함수
     then-statement             ;;  조건이 참일 경우 실행
     else-statement )           ;;  조건이 거짓일 경우 실행
    ```
    * `if-let` : 식을 평가한 결과를 심볼에 바인딩 한 후, `if` 문과 유사하게 동작. `let`으로 바인딩 한 후, `if` 문으로 넘기는 것보다 간결함
* `when` : 진위 함수의 결과가 참이면 본문을 평가하고, 참이 아니면 `nil`을 반환
    * `when-let` : (예시) `(when-let [need-to-grow-small true] "drink bottle") ;=> "drink bottle"`
* `cond` : 1) 검사식과 2) 그 검사식이 참일 때 평가될 식 을 쌍으로 받는다. **패턴매칭...?**
    * 다른 언어의 `if/else if`와 유사
    * 디폴트 절을 추가하고 싶을 경우, 맨 마지막 검사식 자리에 `:else` 키워드를 넣는다.
    ```clojure
    (let [bottle "mystery"]
        (cond
            (= bottle "poison") "don't touch"
            (= bottle "drinkme") "grow smaller"
            (= bottle "empty") "all gone"
            :else "unknown"))
    ;=> "unknown"
    ```
    * 여기서 `:else` 는 논리적 참으로 평가된다.
* `case` : `cond`에서 검사할 심볼이 같고, 그 값을 `=`로 비교할 수 있는 경우 사용
    * `cond`와 달리, 참인 절이 없는 경우 예외를 발생시킨다.
    * `case`에 기본값을 제공하려면, 맨 마지막에 하나의 식을 주면 된다.

## 함수를 만드는 함수 (High-order function...?)

* `커링(currying)` : 다중 인수를 갖는 함수를 여러 개의 단일 인수 함수들로 연결(chain)하는 방식으로 변환
    * 클로져에서는 `partial` 함수 이용
    * 하스켈의 경우, 다중 인수를 가지는 함수에 arity 보다 적은 수의 인수를 넘겨줬을 때에도 커링이 가능하다.
    ```haskell
    mult :: (Int -> (Int -> Int))
    mult a b = a * b
 
    m = mult 6
    show (m 4) -- 24
    ```
* `comp` 함수는 임의의 함수들을 인자로 받아 새로운 합성 함수를 반환한다.
    * 합성 함수는 인수로 받은 함수들을 오른쪽부터 왼쪽으로 실행
    * `comp`는 일종의 syntatic sugar로 생각할 수 있다.
        * `comp`가 없을 경우, 엄청난 괄호 지옥이(....)


## 구조분해 (destructuring)
코드를 분해하여 심볼에 바인딩 한다!
```clojure
(let [[color size] ["blue" "small"]]
  (str "The " color " door is " size))
;=> "The blue door is small"
```
color와 size 심볼에 값이 할당되는 것을 볼 수 있다.
* `구조분해`는 바인딩 식(binding expression)에서 심볼들의 위치에 따라 어떤 값을 어떤 심볼에 바인딩할지 결정한다.
* 벡터 구조분해를 사용하면 순차적인 자료구조를 분해할때 더 간결하고 읽기 쉬우면서도 우아한 코드를 만들 수 있다.
```clojure
; 구조분해는 중첩된 경우에도 아주 쉽게 처리한다.
(let [[color [size]] ["blue" ["very small"]]
  (str "The " color " door is " size))
;=> ~~~~
```
* 구조분해를 통해 원하는 값을 심볼로 바인딩하면서, 처음 자료구조 전체를 얻고 싶다면 `:as` 키워드를 사용하자.
```clojure
(let [[color [size] :as original] ["blue" ["small"]]]
  {:color color :size size :original original})
;=> {:color "blue", :size "small", :original ["blue" ["small"]]}
```
* 구조분해는 맵에도 역시 적용 가능하다. 
  * let에서 맵의 키에 대응되는 값을 심볼에 바인딩
```clojure
(let [{flower1 :flower1 flower2 :flower2}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))
;=> "The flowers are red and blue"
```
* `:or` 키워드를 쓰면, 맵에 해당요소가 없을 때를 위한 디폴트 값을 설정할 수 있다.
```clojure
(let [{flower1 :flower1 flower2 :flower2 :or {flower2 "missing"}}
      {:flower1 "red"}]
  (str "The flowers are " flower1 " and " flower2))
;=> "The flowers are red and missing" 
```
* 맵에서도 역시 `:as` 키워드를 써서 처음 자료구조 전체를 얻을 수도 있다.
```clojure
(let [{flower1 :flower1 :as all-flowers}
      {:flower1 "red"}]
  [flower1 all-flowers])
;=> ["red", {:flower1 "red"}]
```
* 심볼 이름을 키의 이름과 같게 할 수 있으므로 `:keys` 키워드를 사용해서 간결하게 표현할 수 있다.
```clojure
(let [{:keys [flower1 flower2]} ;=> {flower1 :flower1 flower2 :flower2}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))
;=> "The flowers are red and blue"
```
* `defn`으로 함수를 정의할 때도 함수의 인수에 대해서 구조분해를 적용할 수 있다.
```clojure
(defn flower-colors [{:keys [flower1 flower2]}]
  (str "The flowers are " flower1 " and " flower2))

(flower-colors {:flowers "red" :flower2 "blue"})
;=> "The flowers are red and blue"
```

## 지연(lazyness)
* `range` 함수는 lazy sequence 를 반환한다. 
  * 인수 하나를 주면 범위의 끝을 지정할 수 있다.
  * 끝을 지정하지 않으면 무한 시퀀스가 된다(...)
* `take` 함수는 전체 무한 시퀀스를 평가해서 결과를 만들어내는 대신 요구하는 갯수만큼만 평가한다.
```clojure
(take 10 (range))
;=> (0 1 2 3 4 5 6 7 8 9)
```
* `repeat` 함수는 반복된 요소로 된 무한 시퀀스를 만든다. `range` 처럼 반복할 횟수를 인수로 받는다.
```clojure
(repeat 3 "rabbit")
;=> ("rabbit" "rabbit" "rabbit")

(take 5 (repeat "rabbit"))
;=> ("rabbit" "rabbit" "rabbit" "rabbit" "rabbit")
```
* `repeat`는 값은 값을 반복해서 시퀀스로 생성하는 반면, `repeatedly`는 인수로 받은 함수를 반복해서 실행한다.
  * 인수가 없는 함수를 받기 때문에, 함수를 인자로 넘길때 무명함수로 바꿔줘야 한다.
```clojure
(take 10 (repeatedly #(rand-int 10)))
;=> (9 9 5 8 3 1 0 9 3 2)
```
* `cycle` : 컬렉션을 인수로 받아서 그 컬렉션의 요소들이 무한히 반복되는 지연 시퀀스를 반환한다.
```clojure
(take 3 (cycle ["big" "small"]))
;=> ("big" "small" "big")
```
### 지연이 왜 필요한가?
* 일반적이고 우아한 방식으로 코드를 작성?
* 실제 처리할 필요가 있는 것만 사용
  * 계산량이 많은 작업을 처리할 때나 데이터베이스에 요청하고 필요할 때 그 결과(offset..?)를 나누어서 받을 때 유용

## 재귀
시퀀스를 순회하는 방법.



## 함수형 프로그래밍에서의 데이터 변환

### map

### reduce