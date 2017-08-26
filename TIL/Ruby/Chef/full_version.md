# Chef
* 클러스터의 컴퓨팅머신들을 관리하는데 쓰이는 오케스트레이션 툴.

## 용어
* 노드 : Chef에 의해 관리되는 컴퓨터
* Role : 노드가 어떤 역할을 하는 집단에 속하는지를 나타내는 용어

## 구성요소

* 리소스 : Chef의 리소스는 시스템의 일부를 나타낸다.
    * 리소스에는 파일, 패키지, 서비스 같은 것들이 포함된다.
* 레시피 : 웹서버, DB서버, 로드밸런서 등을 세팅하기위해 필요한 관련된 리소스들을 묶은 파일
* 쿡북 : configuration, policy distribution 등의 목적으로 사용되는 기본적인 단위
    * chef 확장기능, 레시피, 템플릿 등을 포함한다.
* 데이터 백 : 쉐프 서버에서 접근가능하며, JSON 의 형식으로 주어지는 전역변수
    * 인증서, API 키, 비밀번호 등과 같은 정보를 암호화시킨 형태로 저장되기도 한다.
    * `data_bag_item('passwords', 'mysql')` : password라는 데이터백에서 mysql이라는 이름을 가진 데이터백 아이템을 불러온다
* attribute : 반복적으로 하드코딩된 부분을 줄이는데 도움이 된다. (상수로 써먹는 역할)

## Chef 클러스터를 이루는 요소들

### Chef server
* 쿡북/레시피를 저장해두는 역할
* 관리되는 노드들의 정보를 DB에 저장(postgres)

### Workstation
* Chef 서버와 통신할 수 있는 디바이스(ssh로 연결됨)
* knife 같은 커맨드라인 툴로 쿡북/레시피를 업로드
* 노드를 등록할 때는 knife bootstrap 명령으로 chef-client를 옮겨주는 작업을 해야함
    * chef-client : 의도한대로 시스템을 세팅할 수 있도록 레시피를 chef 서버에서 내려받아서 실행
* 노드에 역할부여하고, 각 역할마다 어떤 일 수행할지 지정

### Node
* Chef 서버에 의해 관리되는 서버들
* 부트스트랩 과정에서 Chef 서버에서 chef-client를 내려받아 쿡북에 명시한대로 시스템이 세팅됨
* Mesos agent로 사용할 노드, Mesos master로 사용할 노드, Zookeeper로 사용할 노드, Ejabberd를 사용할 노드를 따로따로 지정하고 싶을 때 role 을 지정해준다. 

# 주로 쓰이는 파일
## metadata.rb
* `chef generate cookbook` 명령을 실행하여 쿡북을 생성할때 자동으로 생성되며, 쿡북에 대한 대략적인 정보를 서술할때 쓰인다. 버전 정보, 라이센스, 작성자 정보, Description 등을 명시한다.

## Berksfile
* cookbook의 dependency를 관리하는 파일

# 명령어 정리

* `chef-client`
    * 의도한대로 시스템을 세팅할 수 있도록 chef 코드를 chef 서버에서 내려받아서 실행한다.
    * `--local-mode filename` : 서버가 로컬에 있을 경우 별도로 표시하는 플래그이다.
        * `filename` : filename 에 명시되어있는대로 명령을 커맨드를 실행. 
    * `--runlist 'recipe[learn_chef_httpd]'` : 명시한 여러 쿡북에 정의된 레시피대로 명령을 실행하겠다는 의미
        * `recipe[learn_chef_http]`는 `recipe[learn_chef_http::default]`와 동일함 
    * `chef-client` 로 갱신후 diff 결과가 차이 없을때는 그냥 내버려둔다. (`up-to-date`)


* `knife`
    * 로컬 chef 리포지토리와 chef server의 인터페이스를 제공해주는 CLI 툴
    * `ssh`
        * 지정한 머신에 ssh를 통해 접속
    * `bootstrap`
        * 부트스트래핑은 Chef 서버에 노드를 등록하고, 등록한 노드에 chef-client를 설치하여, 점검하는 과정을 의미한다. 따라서, one-time process 이다.
        * ssh 키 기반 인증 : `knife bootstrap ADDRESS --ssh-user USER --sudo --identity-file IDENTITY_FILE --node-name node1-centos --run-list 'recipe[learn_chef_httpd]`
        * 비밀번호 기반 인증 : `knife bootstrap ADDRESS --ssh-user USER --ssh-password 'PASSWORD' --sudo --use-sudo-password --node-name node1-centos --run-list 'recipe[learn_chef_httpd]'`
            * ex) 해당 노드에 아이디, 패스워드로 접근하여, 특정 role을 부여하고 세팅하는 작업
                * `knife bootstrap $NODE_IP -r 'role[mesos_agent]' -x $ADMIN_ID -P $ADMIN_PASSWORD`
    * `cookbook upload`
        * 쿡북을 Chef 서버에 업로드 할때 쓰이는 명령어이다.
        * `knife cookbook upload learn_chef_httpd`
    * `role`
        * `create` : role을 생성
        * `from file [filename]` : 주어진 파일을 이용하여 role에 대한 configuration이 이루어짐(`.json` 형태여야 함)
        * `show` : 주어진 role에 대한 정보를 출력
        * `list` : role의 목록을 출력
    * `search`
        * `node`
            * `role:[role_name]`
                * 주어진 이름의 role을 가지는 노드를 검색


* `berks`
    * `install` : 로컬에 의존성 라이브러리 설치
    * `upload` : 쿡북과 의존성 라이브러리들을 chef server에 설치



* `chef`
    * `generate`
        * `cookbook` : 쿡북 프로젝트 디렉토리를 생성한다.
        * `attribute default` : 쿡북 프로젝트에서 쓰이는 `default.rb` 파일을 생성한다.
        * `template default.conf` : 템플릿 파일을 생성
        * `recipe` : 레시피 파일 생성


* `kitchen` : 테스트 목적으로 쓰는 커맨드

# DSL

## file

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

## group


## user
* `group` : 사용자가 어느 그룹에 속하는지 명시
* `system` : 
* `shell` : 해당 사용자가 로그인 시 사용하는 쉘


## remote_file `filename`
* `source` : 어디서 내려받는 파일인지 명시하고 `filename`의 경로에 저장
    * http, sftp, ftp, local(`file://`) 등 여러가지 원격 파일의 위치를 명시한다.
    * 여러개의 소스를 명시할 수 있다.
* `owner` : 주어진 파일의 접근권한을 가진 사용자 설정
* `group` : 주어진 파일에 대한 접근권한을 가진 그룹 설정
* `mode` : 주어진 파일의 접근권한 설정

## template `filename`
* `source` : `.erb` 확장자로 되어 있는 템플릿 파일을 불러온다
* `variables( args )` : 해시의 형태로 템플릿에 채워넣을 변수의 값을 지정해준다. 