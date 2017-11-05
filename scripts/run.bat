@ECHO off

set postgresURL=jdbc:postgresql://localhost:5432/project-statistics
set /p postgresURL=Postgres url [%postgresURL%]:

set loggingFile=app.log
set /p loggingFile=Log file [%loggingFile%]: 

java -Dspring.datasource.url=%postgresURL% -Dlogging.file=%loggingFile% -jar project-statistics-server-1.0.0.jar
