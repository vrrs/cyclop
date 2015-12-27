Cyclop
===================================
Restful API for image recognition. 
Root endpoint is /cyclop/v1/

Admin Endpoints
```GET /cyclop/v1/admin/info```
```GET /cyclop/v1/admin/health```
```POST /cyclop/v1/admin/shutdown```

To Run locally
=============================================================================================
1.Run a Mysql Server and create root credentials with no password
2.Run SQL script src/main/resources/cyclop-model-schema.sql against mySql Server
3.Build jar ```mvn clean install```
4.Run it ```java -jar ${JAR_ABSOLUTE_PATH}/${JAR_NAME}```. Cyclop will run on port 8080.
