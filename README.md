# notes-core

Join the Discord server if you want to collaborate: https://discord.gg/CcsTmy

Example request with HTTPie for searching and pagination (all params are optional):
```
http get "localhost:8080/notes?page=0&size=2&sort=message,asc&sort=id,desc"
```