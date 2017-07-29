# 쿼리를 모아서 저장하기
* 사용하게 될 쿼리들은 `resources/sql/queries.sql`에 저장하는 것이 관습
* `conman`을 통해서 DB를 연결하고, 주석에서 설명한 함수의 이름을 찾아서 접근 후 불러온다

```sql
- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id
```