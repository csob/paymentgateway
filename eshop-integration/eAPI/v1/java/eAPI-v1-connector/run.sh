#!/bin/bash
#
SCRIPT="$0"

# SCRIPT may be an arbitrarily deep series of symlinks. Loop until we have the concrete path.
while [ -h "$SCRIPT" ] ; do 
  ls=`ls -ld "$SCRIPT"`
  # Drop everything prior to ->
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    SCRIPT="$link"
  else
    SCRIPT=`dirname "$SCRIPT"`/"$link"
  fi
done
 
SERVER_HOME=`dirname "$SCRIPT"`
SERVER_HOME=`cd $SERVER_HOME; pwd`

if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME not set"
    exit
fi

JAVA=$JAVA_HOME/bin/java
$JAVA -jar $SERVER_HOME/target/eAPI-v1-connector-1.0.0-SNAPSHOT.jar $*
 