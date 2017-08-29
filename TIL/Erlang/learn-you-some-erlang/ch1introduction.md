# introduction 

## So what's Erlang?

Erlang은 함수형 프로그래밍을 따른다.
* 같은 입력에 따라 항상 같은 결과를 반환하는 참조 투명성(referential transparency)을 보장한다.


`Erlang has this very pragmatic approach with functional programming: obey its purest principles (referential transparency, avoiding mutable data, etc), but break away from them when real world problems pop up.`
* Erlang은 함수형 프로그래밍에 실용적인 접근을 취한다. "왠만하면 mutable을 피하고 참조투명성을 지향하면서, real world와 관련된 문제일때는 위반해도 좋다".
    * 계속 변하는 날짜, Unix timestamp 등등..


Erlang은 동시성(concurrency)을 지원하기 위해 Actor 모델을 이용하고 있다.
* actor는 가상 머신 위의 독립적인 프로세스이며, 격리된 상태에서 actor끼리 서로 메시지를 주고 받는다.

Erlang은 어떤 운영체제에서도 돌아가며, Erlang이 설치되면 디버거/프로파일러/컴파일러/테스트프레임웤/OTP/웹서버/Mnesia(DB) 등이 같이 설치된다.

## Don't drink too much Kool-Aid

Erlang이 고가용성을 제공하는 이유 중 큰 하나가 VM에서 돌아가는 경량프로세스 때문이기도 하지만, task를 너무 잘게 쪼개서 지나치게 프로세스를 많이 띄워놓으면 오히려 성능저하를 가져올 수도 있다.

Core가 많을 수록 처리속도가 늘어난다고는 하지만 사실 그렇지만은 않다 -> throughput이 늘어난다는 걸 의미하는 것으로 보임

디바이스 드라이버, 이미지/신호처리에는 쓰일만하지 않을 수도 있으나, 서버에서 쓰일 목적의 소프트웨어에서 쓰인다면 빛을 발휘하는 편이다.
* 큐, 맵-리듀스, higher-level protocol implementation

## Where to get Help
모듈에 대한 매뉴얼은 `erl -man lists` 명령을 사용하자

레퍼런스는 다음을 참고하도록 하자
* http://erlang.org/doc/
* http://erldocs.com/

코딩 컨벤션은 다음을 참고하도록 하자
* http://www.erlang.se/doc/programming_rules.shtml

커뮤니티에서 다른 개발자들에게 도움을 얻을 수도 있다.
* 메일링 리스트 : http://www.erlang.org/community/mailinglists
* IRC : irc.freenode.net/ 의 #erlang

특정 문제를 해결하기 위해 미리 만들어진 소스코드를 참고하고자 하는 개발자라면 다음의 사이트를 찾아볼 수도 있다.
* http://www2.erlangcentral.org/