
# 특정패턴을 만족하는 내용이 들어간 파일을 찾을때 
```sh
find . -name "*.c" -print | xargs grep "pattern"
```