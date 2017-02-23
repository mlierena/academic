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

## 지연(laziness)
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
  * 계산량이 많은 작업을 처리할 때나 데이터베이스에 요청하고나서 필요할 때 그 결과(offset..?)를 나누어서 받을 때 유용

## 재귀
시퀀스를 순회하는 방법. 
재귀함수는 스스로를 되불러 온다.
clojure를 비롯한 함수형 언어에서 자료구조를 순회할 때 재귀함수를 이용한다.

```clojure
(def adjs [" normal" "too small" "too big" "is swimming"]) 
(defn alice-is [in out] 
  (if (empty? in)  ;; in이 비어있는지 확인
    out            ;; empty? 가 true를 반환하면 out을 반환
      (alice-is    ;; empty? 가 false를 반환하면 alice-is 를 호출
        (rest in) 
        (conj out 
          (str "Alice is " (first in)))))) 
(alice-is adjs []) 
;; -> [" Alice is normal" "Alice is too small" "Alice is too big" "Alice is swimming"]
```

위의 예제에서, 재귀함수의 가장 핵심적인 부분은 `first`와 `rest`이다.
결과값을 만들어내는데 `conj`를 이용하고, 매개변수들을 재귀 호출 때마다 새로운 값으로 다시 바인딩하고, 리스트가 비어있으면 함수를 종료한다.

### `loop` 를 이용한 재귀함수 작성
위의 예제 코드에서, `loop` 키워드를 이용하면 코드를 좀 더 간결하게 짤 수 있다.
```clojure
(defn alice-is [input]
  (loop [in input
          out []
    (if (empty? in)
      out
      (recur (rest in)          ;; 재귀함수를 다시 호출한다.
                                ;; 이는 loop 의 시작부분으로 jump 하도록 해준다.
                                ;; recur는 이름이 있는 함수를 불러오는 것을 대신해준다.
              (conj out
                (str "Alice is " (first in))))]))
```

`loop`와 `recur`는 이름이 있는 함수보다 더 간결한 코드를 짤 수 있게 해준다.
이름이 있는 함수를 재귀함수로 작성할 때와는 달리, `recur`는 인수를 하나만 넘겨주면 된다.

`recur`를 이용해서 재귀함수를 작성하면, 재귀 호출 때마다 `loop` 부분으로 점프하기 때문에, 스택 프레임의 낭비를 줄일 수 있다는게 큰 장점이다.
* 이런 최적화 기법을 `꼬리 최적화(tail recursion)`이라 한다.
* `recur`를 이용하지 않고 재귀함수를 작성하게 될 경우, 충분히 큰 입력을 받았을 때 `StackOverflowError`를 발생시킨다.

`recur`는 Clojure가 스택의 낭비를 어떻게 줄이는지 보여준다고 할 수 있다.


## 함수형 프로그래밍에서의 데이터 변환

컬렉션을 변환하는 방법은 크게 두 가지로 나뉜다. `map`, `reduce`
* `map` : 인수로 들어오는 데이터와 비슷한 모양의 결과값을 반환한다.
* `reduce` : 인수로 들어오는 데이터의 모양을 바꾼다. 
  * 반환되는 결과값의 원소의 갯수를 바꿀 수 있다.

이게 무슨 소리냐 ㅋㅋ 다음을 봅시다

### map

`map`은 함수와 컬렉션을 인수로 받는다. 
`map`의 반환값은 컬렉션의 각 원소에 함수를 적용한 뒤의 결과값이다.

```clojure
(defn map [function coll]
  (loop [in coll
         out []] 
            (if (empty? in)
              out
              (recur (rest in)
                (conj out 
                  (function (first in)))))))
```
`map`은 위와 같은 코드를 추상화한 것이라 보면 된다.

이해를 위해 예시코드를 보자.
돌아가는 모습을 보이기 위해, 컬렉션과 함수를 미리 정의해두겠다.

```clojure
(def animals [:mouse :duck :dodo :lory :eaglet])
(#(str %) :mouse) ; str 함수는 심볼을 문자열로 바꿔줄 수 있다. 
;; -> (":mouse")
(map #(str %) animals)
;; -> (":mouse" ":duck" ":dodo" ":lory" ":eaglet")
```
벡터를 인수로 넘겼는데, 벡터를 반환하지 않는다. 왜일까? 한번 확인해보자.

```clojure
(class (map #(str %) animals))
```
`map`은 lazy 시퀀스를 반환한다! 
lazy 시퀀스가 의미하는 것은 즉, 무한의 시퀀스를 다룰 수 있다는 것과 같다.

모든 정수를 다루는 무한 시퀀스에 map을 한번 적용해보도록 하자.
```clojure
(take 3 (map #(str %) (range)))
;; -> ("0" "1" "2")
```
#### Side-effect 와 같이 쓸 때는 주의할 것!
side-effect와 map을 같이 쓸 때는 주의가 필요하다.

side-effect가 없는 순수한 함수의 경우, 외부와의 상호작용이 없이 같은 입력이 주어지면 항상 같은 결과값을 반환한다.

반면, side-effect는 바깥 범위의 어딘가를 건드리는 함수에서 발생한다.
* 예를 들자면, 콘솔 창에 출력한다던가, 파일에다가 로그를 갱신한다던가, 상태를 변화시키는 것들이다.

side-effect와 laziness를 이용해서 함수를 작성할 때, side-effect가 원할 때만 발생해야한다는 것을 염두에 두고 있어야 한다.

먼저, `println` 함수가 어떻게 동작하는지 살펴보자
```clojure
(println "Look at the mouse")
;; Look at the mouse!
;; -> nil           ;; 인수를 받아서 출력 스트림에 출력(side-effect)한다.
```

문자열을 반환하는 대신 출력하는 함수를 `map`에 인수로 넘긴 결과를 var에 바인딩했을 때 어떤 결과를 남기는지 확인해보자.
```clojure
(def animal-print (map #(println %) animals))
;; -> #'user/animal-print
```
시퀀스가 소모되지 않았기 때문에, side-effect가 생성되지 않았다. (laziness 때문)

값을 불러오고자 할 경우, 다음과 같은 결과를 볼 수 있다.
```clojure
animal-print
;; mouse
;; :duck
;; :dodo
;; :lory
;; :eaglet
;; -> (nil nil nil nil nil)
```

side-effect의 평가를 강제하고 싶을 경우, `doall`을 쓸 수 있다.
```clojure
(def animal-print (doall (map #(println %) animals)))
;; mouse
;; :duck
;; :dodo
;; :lory
;; :eaglet
;; -> #'user/animal-print

animal-print
;; -> (nil nil nil nil nil)
```

#### `map`에 두 개 이상의 컬렉션을 인수로 넘길 수 있다.
```clojure
(def animals
  ["mouse" "duck" "dodo" "lory" "eaglet"])
(def colors
  ["brown" "black" "blue" "pink" "gold"])

(defn gen-animal-string [animal color]
  (str color "-" animal))

(map gen-animal-string animals colors)
;; -> ("brown-mouse" "black-duck"
;;    "blue-dodo" "pink-lory" "gold-eaglet")
```

위의 경우, `map` 함수는 가장 길이가 짧은 컬렉션에 대한 순회가 끝날때 종료한다.

```clojure
(def animals
  ["mouse" "duck" "dodo" "lory" "eaglet"])
(def colors
  ["brown" "black"])

(map gen-animal-string animal colors)
;; -> ("brown-mouse" "black-duck")
```

`cycle`로 무한리스트를 생성해서 `map`에 인수로 넘길 수 있다.
```clojure
(map gen-animal-string animal (cycle colors))
;; -> ("brown-mouse" "black-duck" "brown-dodo" "black-lory" "brown-eaglet")
```

### reduce

```clojure
(reduce + [1,2,3,4,5])
;; -> 15
```

일반적으로, `reduce`는 두 개의 인수를 받는 함수(arity가 2인 함수)를 인수로 받는다.
두 개의 인수는 진행중인 결과값(현재 상태)과 처리하게 될 원소를 의미한다.

다음은, 제곱의 합을 구하는 함수를 `reduce`로 넘긴 예시이다.
컬렉션의 원소의 제곱을 구한 다음, 이전 결과값에 덧셈을 한다. 
```clojure
(reduce (fn [r x] (+ r (* x x)) [1 2 3]))
;; -> 14
```

첫번째 원소를 처리하기 전에, 초기값을 `reduce`에 넘겨줄 수 있다.
다음 예시는 컬렉션에서 nil이 제외된 컬렉션을 반환하는 코드이다. 
```clojure
(reduce (fn [r x] (if (nil? x) r (conj r x))) 
        []
        [:mouse nil :duck nil nil :lory])
;; -> [:mouse :duck :lory]
```

`reduce`는 `map`과는 달리 무한 리스트에 대해서 적용될 수 없다.
입력으로 주어진 컬렉션이 빌 때까지 계속 돌아가기 때문이다(....)

### 결론
`map`과 `reduce`는 데이터를 조작하기 위해 쓰이는 고도로 추상화된 함수라고 할 수 있다.
* 거기다 이것들이 전부 재귀로 구현되어 있다.

## 데이터를 변환하는데 쓰이는 다양한 함수들

### filter

`filter` 함수를 쓰기 전에, 먼저 `complement` 함수를 먼저 알아보자. 

#### complement
`complement`는 함수를 인수로 받아, 똑같은 인수를 받는 함수를 반환한다.
`complement`에서 반환하는 함수는 원래 함수에서 반환하는 불리언 값의 정반대의 값을 반환한다. 

```clojure
((complement nil?) nil)
;; -> false
((complement nil?) 1)
;; -> true
```

#### filter에 적용해보기
```clojure
(filter (complement nil?) [:mouse nil :duck nil])
;; -> (:mouse :duck)

;; 키워드인지 아닌지 검사하는 함수를 인수로 넣었을 경우
(filter keyword? [:mouse nil :duck :nil])
;; -> (:mouse :duck)
```

### remove
`filter`랑 반대로 동작함
```clojure
(remove nil? [:mouse nil :duck nil])
;; -? [:mouse :duck]
```

### for
접근하고자 하는 컬렉션의 각 원소를 바인딩하여 함수의 몸체에서 처리한다.
반환결과는 역시 lazy 시퀀스.

```clojure
(for [animal [:mouse :duck :lory]]
  (str (name animal)))
;; -> ("mouse" "duck" "lory")
```

두 개의 컬렉션을 인수로 넘길 경우, 중첩된 형태로 컬렉션 각각을 순회한다.
* ~~곱집합...?~~
* 사실 그냥 2중 for문을 생각하면 된다.

```clojure
(for [animal [:mouse :duck :lory]
      color [:red :blue]]
      (str (name color) (name animal)))
;; -> ("redmouse" "bluemouse" 
;;      "redduck" "blueduck" 
;;      "redlory" "bluelory")
```

`:let` 지시어(modifier)를 사용하여 `for` 내부에서 let 바인딩을 할 수 있다.

```clojure
(for [animal [:mouse :duck :lory]
      color [:red :blue]
      :let [animal-str (str "animal-" (name animal))
            color-str (str "color-" (name color))
            display-str (str animal-str "-" color-str)]]
      display-str)
;; -> ("animal-mouse-color-red" "animal-mouse-color-blue"
;;    "animal-duck-color-red" "animal-duck-color-blue"
;;    "animal-lory-color-red" "animal-lory-color-blue")
```

조건이 true를 만족할때만 식이 평가될 수 있도록 `:when` 지시어를 사용할 수 있다.
```clojure
(for [animal [:mouse :duck :lory]
      color [:red :blue]
      :let [animal-str (str "animal-" (name animal))
            color-str (str "color-" (name color))
            display-str (str animal-str "-" color-str)]
      :when (= color :blue)]
      display-str)
;; -> ("animal-mouse-color-blue"
;;    "animal-duck-color-blue"
;;    "animal-lory-color-blue")
```

### flatten
`flatten`은 중첩으로 구성된 컬렉션을 인수로 받아 평탄해진 시퀀스를 반환한다.
```clojure
(flatten [[:duck [:mouse] [[:lory]]]] )
;; -> (:duck :mouse :lory)
```

### into
`into`는 새 컬렉션을 인수로 받아 컬렉션의 모든 원소가 인수로 받은 새 컬렉션에 `conj`된 것을 반환한다.

```clojure
(into [] '(1 2 3))
;; -> [1 2 3]
```

`into`는 맵을 `sorted-map`에 적용할 때도 사용할 수 있다.

#### sorted-map
`map`에 순서가 들어간 것이라고 생각하면 된다.
```clojure
(sorted-map :b 2 :a 1 :z 3)
;; -> {:a 1, :b 2, :z 3}

(into (sorted-map) {:b 2 :c 3 :a 1})
;; -> {:a 1, :b 2, :c 3}

;; 페어의 벡터를 맵에 넣을 수도 있다.
(into {} [[:a 1] [:b 2] [:c 3]])
;; -> {:a 1, :b 2, :c 3}

;; 맵을 페어의 벡터로 만들 수도 있다.
(into [] {:a 1, :b 2, :c 3})
;; -> [[:c 3] [:b 2] [:a 1]]
```

### partition
`partition`은 컬렉션을 나눌 때 쓰인다.

다음의 코드는 컬렉션을 3개의 원소로 쪼갠다.
```clojure
(partition 3 [1 2 3 4 5 6 7 8 9])
;; -> ((1 2 3) (4 5 6) (7 8 9))

(partition 3 [1 2 3 4 5 6 7 8 9 10])
;; -> ((1 2 3) (4 5 6) (7 8 9))
```

#### partition-all
기본적으로 `partition`은 마지막으로 분할 조건을 만족하는 부분까지만 분할한다.
마지막에서 보인 경우는 10이라는 원소가 제외됬다. 
제외되는 원소까지도 같이 분할에 포함시키려면 `partition-all` 을 써보자.

```clojure
(partition-all 3 [1 2 3 4 5 6 7 8 9 10])
;; -> ((1 2 3) (4 5 6) (7 8 9) (10))
```

### partition-by 

컬렉션에 적용할 함수와 컬렉션을 인수로 받으며, 함수의 결과가 변할 때마다 새로운 분할을 생성한다.
```clojure
(partition-by #(= 6 %) [1 2 3 4 5 6 7 8 9 10])
;; -> ((1 2 3 4 5) (6) (7 8 9 10))
``` 