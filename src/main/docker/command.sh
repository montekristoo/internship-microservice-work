#!/usr/bin/zsh

CMD="java -jar global-transaction-docker.jar"
$CMD &
SERVICE_PID=$!

#Execute Tests
mvn test

#Wait for Spring execution
wait "$SERVICE_PID"