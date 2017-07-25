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
        * 부트스트래핑은 Chef 서버에 노드를 등록하여 chef-client를 설치하여, 점검하는 과정을 의미한다. 따라서, one-time process 이다.
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
