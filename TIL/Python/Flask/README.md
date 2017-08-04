# Overview
* Python 기반으로 작성된 마이크로 웹 프레임워크

```python
import flask from Flask, ...(flask에 정의된 클래스/모듈들..)
```

# Flask에서 사용되는 모듈들
* `jsonify` : JSON 형태로 데이터를 가공하기 위해 쓰이는 모듈
* `g` : 스레드와 각각의 request 내에서만 값이 유효한 스레드 로컬 변수. 사용자의 요청이 동시에 들어오더라도 각각의 request 내에서만 g 객체가 유효하기 때문에 사용자 ID를 저장해도 문제가 없음.
* `render_template` :
