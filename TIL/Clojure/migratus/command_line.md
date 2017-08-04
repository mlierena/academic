# 마이그레이션 생성
* `lein migratus create migration_name`
    * prefix에 timestamp가 같이 찍혀서 마이그레이션 파일이 생성. (.sql 이므로 SQL 지식은 필수)
* 마이그레이션 SQL 파일에서 여러개의 statement를 실행시키고 싶을 경우, `--;;`로 구분해주도록 한다.
    ```sql
    CREATE TABLE IF NOT EXISTS quux(id bigint, name varchar(255));
    --;;
    CREATE INDEX quux_name on quux(name);
    ```
* 

# 마이그레이션 실행
* `lein migratus migrate`

# 마이그레이션 취소
* `lein migratus rollback`

# 마이그레이션 전부 down 후, 다시 up
* `lein migratus reset`