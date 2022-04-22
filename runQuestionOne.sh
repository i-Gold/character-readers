#!/bin/bash

if [ ! -d target ]
then
 ./build.sh
fi

java -Dfile.encoding=UTF-8 -classpath ./target/classes:./target/libs/commons-lang3-3.12.0.jar:./target/libs/logback-classic-1.2.11.jar:./target/libs/logback-core-1.2.11.jar:./target/libs/slf4j-api-1.7.32.jar javatest.read_singlestream.Trigger
