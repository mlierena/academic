# Ruby 환경에서 VS Code 세팅


## Intellisense / 자동완성

```sh
gem install rcodetools
gem install fastri
gem install ruby-debug-ide 
gem install debase
```

default 설정에 다음의 코드를 추가
```json
"ruby.locate": {
    "include": "**/*.rb",
    "exclude": "{**/@(test|spec|tmp|.*),**/@(test|spec|tmp|.*)/**,**/*_spec.rb}"
}
```