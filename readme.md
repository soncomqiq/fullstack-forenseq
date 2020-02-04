# How to start server

sudo docker-compose up -d --build

# How to import database

go to sqlserver-forenseq (cd sqlserver-forenseq)

cat all_db_bak.sql | sudo docker-compose exec -T db /usr/bin/mysql -uroot -p12345678 fgxbio

# Port

frontend: localhost:3000

backend: localhost:8080

mysql-server: localhost:3306
