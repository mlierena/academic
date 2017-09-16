# handler
핸들러는 클라이언트의 요청과 서버의 응답을 나타내기 위해 Clojure 에서 지원하는 map 자료구조를 사용한다.

## request-map
* `:server-port`
* `:server-name` : 서버의 IP 주소 혹은 resolve 되는 이름
* `:remote-addr` : 클라이언트 IP 주소
* `:query-string` : 요청의 쿼리 문자열
* `:scheme` : 프로토콜 ex) `:http`, `:https`
* `:request-method` : HTTP 요청의 메서드 ex) `:get`, `:head`, `:options`, `:put`, `:post`, `:delete`
* `:content-type` : request body의 MIME 타입
* `:content-length` : 요청의 바이트수
* `:character-encoding` : 인코딩
* `:headers` :요청의 헤더를 나타내는 맵
* `:body`
* `:context`
* `:url`
* `:ssl-client` : 클라이언트의 ssl 인증서

## response-map
* `:status` : 응답의 HTTP status
* `:headers` : 클라이언트에 보내지는 HTTP 헤더
* `:body`


## header
* header 는 HTTP 헤더를 키와 값의 쌍으로 나타낸 map 으로 표현된다. 값으로 문자열 혹은 문자열의 시퀀스를 받을 수 있다.

```clojure
{:headers {"content-type" "text/html"}
 :body "<html><body>hi</body></html>"}

{:headers {"content-type" ["text/plain" "text/html"]}
 :body "<html><body>hi</body></html>"}

```
