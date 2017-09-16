# lein 명령어 정리

## lein new 

### project.clj 구조

`defproject` 매크로에 들어가는 :main 키에 들어가는 값은, -main 함수를 호출하는 모듈의 이름이다. `-main` 함수는 어플리케이션이 처음 시작하는 엔트리포인트가 되기도 한다.