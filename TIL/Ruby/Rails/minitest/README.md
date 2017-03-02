# Minitest 사용법 메모

## 헬퍼 메서드 끌어오기
테스트 클래스 내부에 불러오고자 하는 모듈을 include 한다. 

## 단일 테스트 파일만 실행

특정 테스트 수트를 정의한 파일의 테스트만 실행하고 싶을 경우 TEST 옵션에 경로를 명시해두자
```sh
bin/rake test TEST=test/models/blah_test.rb
```