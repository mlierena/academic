# Requirements
* gcc
* readline
* bzip

# Installation

```sh
yum install zlib-devel bzip2 bzip2-devel readline-devel sqlite sqlite-devel openssl-devel xz xz-devel

git clone https://github.com/pyenv/pyenv ~/.pyenv
echo 'export PYENV_ROOT="$HOME/.pyenv"' >> ~/.bash_profile
echo 'export PATH="$PYENV_ROOT/bin:$PATH"' >> ~/.bash_profile

echo 'eval "$(pyenv init -)"' >> ~/.bash_profile

source ~/.bash_profile

# Flask dependency
pip install flask sqlalchemy requests

```


# Reference 
* Common Problems : https://github.com/pyenv/pyenv/wiki/Common-build-problems
