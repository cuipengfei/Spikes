
spring integration jdbc distributed lock, threads and transactions

```
docker run -e POSTGRES_USER=localtest -e POSTGRES_PASSWORD=localtest -e POSTGRES_DB=orders -p 5432:5432 -d postgres:15.3
```

```
docker run -p 6379:6379 -d redis:7.0.12 --requirepass "mypass"
```