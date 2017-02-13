# Git command 정리


## 기본적인 사용방법

* `git init` : 리포지토리 초기화
* `git status` : 리포지토리 상태 확인
    * 어느 브랜치에 있는지, commit 하기 전 Git의 working tree에 어떤 변화가 생겼는지 표시
* `git add` : 스테이지 영역에 파일 추가
    * 여기서 스테이지 영역은 `commit` 하기 전의 임시 영역
* `git commit` : 리포지토리 변경 내용을 기록
    * 한 줄에 commit 메시지를 기록하고 싶을 경우 `git commit -m "message"`
        * commit 메시지를 상세하게 기록하고 싶을 경우 -m 플래그 생략. 기본 에디터가 nano 또는 vi
            * commit 메시지를 작성할 에디터를 변경하고 싶을 경우, `git config --global core.editor "/usr/bin/emacs"` 와 같이 변경
            * 에디터가 실행된 상태에서 commit 을 중지하고 싶을 경우, 에디터를 강제종료하면 된다.
    * `git commit --amend` : 커밋 메시지 수정
* `git log` : commit 확인
    * 누가 언제 commit 또는 merge를 했는지, 어떤 변경이 발생했는지 확인
    * `git log --pretty=short` : commit 메시지의 첫번째 요약줄만 표시
    * `git log filename` : 해당 파일과 관련된 로그만 표시
    * `git log -p` : commit 에서 변경된 내용을 확인
        * `git log -p filename` : 특정 파일의 commit 로그와 변경 내용 출력
    * `git log --graph` : 브랜치를 시각적으로 확인. 브랜치가 분기되고 통합되는 모양을 간단하게 표시
* `git diff` : 변경 내역 확인
    * working tree, 스테이지 영역, 최신 commit 사이의 변경을 확인할 때 사용
    * `git diff HEAD` : 최신 commit 과의 차이를 확인
        * 여기서 HEAD는 현재 작업하고 있는 브랜치의 최신 commit을 참조하는 포인터
    

## 브랜치 생성
브랜치는 각각의 작업을 병행할 때 사용한다.
* `git branch` : 브랜치의 목록을 표시. 어떤 브랜치를 사용하는지 확인
* `git checkout -b A` : 브랜치를 만들고 변경. 아래와 동치
    ```sh 
    git branch A 
    git checkout A
    ```
    * `git checkout master` : master 브랜치로 변경
    * `git checkout -` : 한단계 전의 브랜치로 변경



#### 토픽 브랜치
하나의 토픽에만 집중하는 브랜치로서 다른 작업은 일절 허용되지 않는다. 
일반적으로 개발할 때, 토픽 브랜치를 여러개 작성해서 사용하며, 이와는 별도로 언제라도 배포할 수 있는 안정된 브랜치를 따로 준비해두어야 한다. 
여기서 master 브랜치가 이 안정된 브랜치라고 할 수 있다.

#### 통합 브랜치


## Git과 관련된 참고 자료

* [Pro Git](http://git-scm.com/book/ko)
* [LearnGitBranching](http://learnbranch.urigit.com)
* [tryGit](http://try.github.io)