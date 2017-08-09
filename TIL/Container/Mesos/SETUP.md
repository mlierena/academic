# Downloading Mesos

왠만하면 안정화된 버전에서 설치하는 것을 권장한다.


# 시스템 요구 사항

Mesos에서 실행되는 몇가지 테스트에서 필요한 Docker의 호스트 네트워킹을 지원하기 위해, 호스트의 이름이 DNS 혹은 `/etc/hosts`를 통해 resolve 될 수 있도록 해둬야 한다.

# CentOS 7.1 버전에서 설치하기

```sh
# 설치에 필요한 몇가지 유틸리티 들을 설치한다.
sudo yum install -y tar wget git

# Apache의 Maven 레포지토리를 가져온다
sudo wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo

# Install the EPEL repo so that we can pull in 'libserf-1' as part of our
# subversion install below.
$ sudo yum install -y epel-release

# 'Mesos > 0.21.0' requires 'subversion > 1.8' devel package,
# which is not available in the default repositories.
# Create a WANdisco SVN repo file to install the correct version:
sudo bash -c 'cat > /etc/yum.repos.d/wandisco-svn.repo <<EOF
[WANdiscoSVN]
name=WANdisco SVN Repo 1.9
enabled=1
baseurl=http://opensource.wandisco.com/centos/7/svn-1.9/RPMS/\$basearch/
gpgcheck=1
gpgkey=http://opensource.wandisco.com/RPM-GPG-KEY-WANdisco
EOF'

# Parts of Mesos require systemd in order to operate. However, Mesos
# only supports versions of systemd that contain the 'Delegate' flag.
# This flag was first introduced in 'systemd version 218', which is
# lower than the default version installed by centos. Luckily, centos
# 7.1 has a patched 'systemd < 218' that contains the 'Delegate' flag.
# Explicity update systemd to this patched version.
sudo yum update systemd

# Install essential development tools.
sudo yum groupinstall -y "Development Tools"

# Install other Mesos dependencies.
sudo yum install -y apache-maven python-devel python-six python-virtualenv java-1.8.0-openjdk-devel zlib-devel libcurl-devel openssl-devel cyrus-sasl-devel cyrus-sasl-md5 apr-devel subversion-devel apr-util-devel
```

# Mesos 빌드하기

```sh
wget http://www.apache.org/dist/mesos/1.3.0/mesos-1.3.0.tar.gz
tar -zxf mesos-1.3.0.tar.gz

cd mesos-1.3.0
mkdir build
cd build
# 이전 폴더의 configure 스크립트를 실행
../configure

# Makefile에 명시된대로 빌드
make
```