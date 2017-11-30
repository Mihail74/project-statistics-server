#!/bin/sh

defaultPostgresURL=jdbc:postgresql://localhost:5432/project-statistics

read -p "Postgres url [$defaultPostgresURL]: " postgresURL
postgresURL=${postgresURL:-$defaultPostgresURL}


defaultLoggingFile=app.log

read -p "Log file [$defaultLoggingFile]: " loggingFile
loggingFile=${loggingFile:-$defaultLoggingFile}


java -Dspring.datasource.url=$postgresURL -Dlogging.file=$loggingFile -jar project-statistics-server-1.0.0.jar
