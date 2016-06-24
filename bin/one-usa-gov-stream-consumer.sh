#!/bin/sh

JAR_FILE='build/one-usa-gov-stream-consumer-1.0-all.jar'
CONFIG_FILE='config/one-usa-gov.stream-consumer.properties'
LOG4J_FILE='config/one-usa-gov.stream-consumer.log4j.properties'
BASE_DIR=$(dirname $0)

java -Dlog4j.configuration=file:$BASE_DIR/../$LOG4J_FILE -jar $BASE_DIR/../$JAR_FILE $BASE_DIR/../$CONFIG_FILE