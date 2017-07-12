# Brunch 로 세팅할 경우

### 설치
`npm install --save-dev stylus-brunch nib`

### brunch-config.js 설정

```js
exports.config = {
// ...

plugins: {
    // ...

    stylus: {
        plugins: ['nib']
    }
}

// ...
}
```
