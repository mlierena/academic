# AWS-SDK



### 업로드 후 주의사항
업로드 후에는 반드시 ACL 권한을 `public-read` 로 설정해주자

```ruby
client.put_object_acl({
  acl: "public-read",
  bucket: bucket,
  key: object_key,
})
```
