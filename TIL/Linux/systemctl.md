# systemctl
* 백그라운드에서 돌아가는 서비스를 꾸준히 실행하고자 할때 쓰인다.
* 부팅/재부팅시에 등록된 서비스를 자동으로 실행하기 때문에 매우 유용하게 쓰인다.

```sh
[Unit]
Description=My Little DNS Server
After=network.target

[Service]
ExecStart=/root/.pyenv/shims/python /home/dns/dns-server/app.py
KillMode=process
Restart=always

[Install]
WantedBy=multi-user.target
```


## Unit
* 서비스에 대한 서술이 들어가는 란이다.

## Service
* `Environment` : 서비스를 시작할때 사용할 환경변수를 지정한다.
    * ~/.bashrc 보다 systemd 가 먼저 호출되기 때문에 별도로 환경변수를 지정해줘야함
* `WorkingDirectory` : 작업을 진행할 현재 디렉토리를 지정한다.
* `ExecStart` : 서비스를 시작할 때 실행할 명령을 지정한다.
* `Restart` : 서비스를 다시 시작할지를 지정한다.

## Install


# journalctl
* systemctl 관련된 로그를 확인할 때 많이 쓴다.