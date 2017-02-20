# String
문자열을 다루는 객체이다.

### 정규식을 이용한 처리

#### String#scan (Regex r)
정규식 r과 패턴이 일치하는 문자열의 배열을 반환한다.
```ruby
upload_key = (image.file.url.scan /user_attachments.*.png/)[0]
```