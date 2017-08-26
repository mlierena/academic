# Luminus
* 웹 개발 용으로 쓰이는 Clojure 기반으로 작성된 웹 프레임워크.
    * Sinatra, Flask 처럼 최소한의 기능만 갖춰진 라이브러리처럼 생각할 수 있다.
* `ring`, `compojure`, `hiccup` 같은 라이브러리를 조합해서 웹개발하는 것이 일반적이었으나, `Luminus`는 좀 더 메커니즘을 간략화한 케이스
* `lein new luminus new_project +postgres +re-frame +reagent` 명령을 통해, 별도로 명시한 라이브러리가 포함된 boilerplate를 생성할 수 있다.

# Luminus 에 쓰이는 라이브러리 정리
* `conman` : DB 연결에 쓰이는 헬퍼 라이브러리
* `HugSQL` : DB 상호작용에 쓰이는 라이브러리
* `migratus` : DB 마이그레이션
