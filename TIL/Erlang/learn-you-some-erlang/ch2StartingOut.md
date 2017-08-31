# Starting Out

## Shell
이미 설치되어 있다면, Unix-like 운영체제 기준으로 `erl` 명령을 실행하면 shell을 띄울 수 있다.

## Shell Commands
Erlang shell은 Emacs의 일부분을 라인 에디터로 내장하고 있다.

텍스트를 입력했을때 `Ctrl + A(^A)` 를 입력하면 줄의 맨 앞으로, `Ctrl + E(^E)` 를 입력하면 줄의 맨 뒤로 커서가 이동한다. 화살표 키(위/아래)를 이용해서 이전의 명령 줄, 그 다음 줄을 왔다갔다 할 수 있다.

자동완성 기능을 제공하기 때문에, `li` 까지만 입력하고 `tab` 키를 입력하면, `lists`로 자동완성 시켜준다.

쉘의 사용방법을 알고 싶을때는 `help().` 를 입력하면 된다. 여기서 `.`는 `종결자(terminator)` 역할을 하기 때문에 반드시 알아둬야 한다. 

종료할때는 `q()` 명령을 사용하자.

쉘이 멈춰버리면 `q()` 명령이 쓸모없기 때문에 `Ctrl + G(^G)` 로 Abort하고 종료시키는 방법이 있다. 이는 `h` 커맨드를 입력하면 자세한 사항을 확인할 수 있다.

```sh
1> # Ctrl + G 로 인터럽트 걸어놓은 상태
User switch command
 --> h
  c [nn]            - connect to job
  i [nn]            - interrupt job
  k [nn]            - kill job
  j                 - list all jobs
  s [shell]         - start local shell
  r [node [shell]]  - start remote shell
  q                 - quit erlang
  ? | h             - this message
``` 

