#!/bin/sh

JAR_FILE='build/kafka-listener-1.0.jar'
CONFIG_FILE='config/kafka-listener.properties'
LOG4J_FILE='config/kafka-listener.log4j.properties'
BASE_DIR=$(dirname $0)

echo $BASE_DIR " is base_dir"

java -Dlog4j.configuration=file:$BASE_DIR/../$LOG4J_FILE -jar $BASE_DIR/../$JAR_FILE $BASE_DIR/../$CONFIG_FILE