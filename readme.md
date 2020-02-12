# FGxBIO v2

## How to start server

```
sudo docker-compose up -d --build
```

## How to import database

go to __sqlserver-forenseq__ (cd __sqlserver-forenseq__)

```
cat all_db_bak.sql | sudo docker-compose exec -T db /usr/bin/mysql -uroot -p12345678 fgxbio
```

## Port

Server | URL and Port
------------ | -------------
Frontend | http://localhost:3000
Backend | http://localhost:8080
MySQL | http://localhost:3306
