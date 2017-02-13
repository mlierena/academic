### Github과 Git의 차이

* `Git` : 소프트웨어의 버전을 관리하기 위한 SW
* `Github` : Git 레포지토리를 인터넷 상에서 제공하는 서비스

### Github에서 제공하는 기능

#### Git 리포지토리
* 기본적으로 무료이지만, private 저장소가 필요할 경우 월 7달러

#### Organization
* 특정 단체 및 집단 명의로 개발할 경우 이용
    * 스터디, IT 계열 커뮤니티, 회사, ...
* 계정 권한 관리, 지불 방법 등을 설정

#### Issue
* 작업 또는 문제를 해결하는 과정을 관리
* 기능 추가/수정, 또는 토론의 목적으로 Issue가 생성됨
* 커밋 메시지에 `#1`과 같이 Issue ID를 추가하면, 해당 이슈에 자동으로 commit 링크가 붙음

#### Wiki
* 문서를 공동으로 작성할 수 있는 기능
* 개발 문서 또는 메뉴얼

#### Pull request
* 다른 사람의 리포지토리에 자신이 push한 변경 사항 또는 기능 추가 사항을 넣어달라고 요구하는 기능
* PR 내용이나 소스코드를 함께 토론하기 위한 기능 제공

### 버전관리

* 버전 관리 시스템은 변경 내역을 관리
    * 소프트웨어의 코드를 추가 또는 변경하는 과정을 모두 기록
        * 특정한 시점으로 돌아가거나,
        * 문제가 생긴 파일을 복원 가능

#### 집중형

* 서브버전(subversion)이 대표적
* 리포지토리가 중앙 서버에만 배치된 형태
    * 관리하기가 무척 단순한 것이 장점이지만,
    * 서버에 접속이 불가능할 경우 최신 소스코드를 받아올 수 있는게 단점...

#### 분산형

* Git이 대표적
* 집중형과는 다르게 리포지토리가 여러 개 존재할 수 있음
* 개인마다 리포지토리를 가질 수 있어, 서버에 있는 리포지토리(원격 리포지토리)에 접속하지 않아도 개발 가능
    * Github의 경우, `Fork`를 통해 특정 리포지토리를 자신의 계정으로 복제

### Git 기본 설정

```sh
git config --global user.name "Firstname Lastname"
git config --global user.email "your_email@example.com"
```

### SSH Key 설정

```sh 
ssh-keygen -t rsa -C "your_email@example.com"
cat ~/.ssh/id_rsa.pub # 공개키 내용을 Github 세팅에 Copy & Paste

ssh -T git@github.com # 확인
```


