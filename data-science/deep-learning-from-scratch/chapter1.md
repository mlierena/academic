# Hello Python

## 기초 문법

* 자료형 알아낼 때는 `type()` 함수를 이용

### 리스트
* 슬라이싱의 사용법을 알아두자
```Python
a = [1,2,3,4,99]
a[0:2]  # [1,2]
a[1:]   # [2,3,4,99] 인덱스 1부터 끝까지 얻기
a[:3]   # [1,2,3]   처음부터 인덱스 3의 앞까지
a[:-1]  # [1,2,3,4] 맨마지막 원소의 앞까지
a[:-2]  # [1,2,3] 
```
### 딕셔너리
* 키-값을 한 쌍으로 저장하는 자료구조
```Python
me = {'height': 100}   # 딕셔너리 생성
print(me['height']) # 100
```

### bool
* 불리언 값은 `True` 또는 `False` 로 나타내야한다. (대문자)
* 사용할 수 있는 연산자로는 `and`, `or`, `not`

### 클래스

```Python
class AAA:
    def __init__(self, arg1, ...):      # 생성자
        ...

    def method1(self, arg1, ...):       # method 1
        ...

    def method2(self, arg1, ...):       # method 2
        ...    
```
* 메서드의 첫번째 인수는 무조건 self를 가진다.
* 인스턴스 변수는 인스턴스별로 저장되며, self 다음에 속성 이름을 써서 접근할 수 있다.

## 외부 라이브러리
* 외부 라이브러리는 `numpy`, `matplotlib`를 사용