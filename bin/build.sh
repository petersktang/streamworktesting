#!/bin/sh

echo "Building streamworktesting projects..."
./gradlew :tools/kafkalistener:build
./gradlew shadowJar

echo "\n\n\nBuild complete."

echo "\n\n\nConfigure properties files under config folder then execute the following commands to run demo:"
echo "bin/kafka-listener.sh"
echo "bin/one-usa-gov-stream-consumer.sh"

