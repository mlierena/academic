# Compojure

요청의 메서드가 GET, POST, DELETE, HEAD, PUT 인지에 따라 응답을 다르게 내뱉을 수 있도록 하기 위한 라우팅 라이브러리

## 명시적인 라우팅

```clojure
(compojure/routes
  (compojure/GET ... )
  (compijure/POST ... ) )
  
```

## 매크로를 이용한 라우팅
* Compojure는 제공된 라우트로부터 ring 핸들러를 만들어낼 수 있도록 defroutes 라는 매크로를 제공한다.

즉, 다음의 두 코드는 동일하다
```clojure
(compojure/defroutes handler
  (compojure/GET "/" request
```