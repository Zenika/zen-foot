#!/bin/bash

export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_79
export PATH=$JAVA_HOME/bin:$PATH

mvn clean package

dev_appserver.sh --jvm_flag=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6006 target/zen-foot-1


