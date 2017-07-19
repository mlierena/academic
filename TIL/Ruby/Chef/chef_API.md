# file

[Resource](https://docs.chef.io/resource_file.html)

```ruby
file '/tmp/motd' do
    content 'hello chef'
end
```

* `action` : 해당 파일에 대해 취할 액션을 정의한다.
    * `:create` : 주어진 파일을 생성. file 블록에서 디폴트로 맨앞에 붙기때문에 필요한 경우가 아니면 딱히 명시할 필요가 없다.
    * `:delete` : 주어진 파일을 삭제한다.
* `content "STRING"` : 해당 파일의 내용을 다음과 같이 쓰여지도록 정의한다.
* `mode` : 접근권한설정. 8진수 형태로 제공 
    * ex) `0644`
* `owner` : 접근 권한이 있는 사용자
* `group` ; 접근 권한이 있는 그룹

# group


# user
* `group` : 사용자가 어느 그룹에 속하는지 명시
* `system` : 
* `shell` : 해당 사용자가 로그인 시 사용하는 쉘


# remote_file `filename`
* `source` : 어디서 내려받는 파일인지 명시하고 `filename`의 경로에 저장
    * http, sftp, ftp, local(`file://`) 등 여러가지 원격 파일의 위치를 명시한다.
    * 여러개의 소스를 명시할 수 있다.
* `owner` : 주어진 파일의 접근권한을 가진 사용자 설정
* `group` : 주어진 파일에 대한 접근권한을 가진 그룹 설정
* `mode` : 주어진 파일의 접근권한 설정