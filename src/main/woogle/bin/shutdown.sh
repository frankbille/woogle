#!/bin/sh

THIS="$0"
while [ -h "$THIS" ]; do
  ls=`ls -ld "$THIS"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '.*/.*' > /dev/null; then
    THIS="$link"
  else
    THIS=`dirname "$THIS"`/"$link"
  fi
done

THIS_DIR=`dirname "$THIS"`

WOOGLE_HOME=`cd "$THIS_DIR/.." ; pwd`

cd $WOOGLE_HOME/lib/jetty
exec java -DSTOP.PORT=18085 -jar stop.jar
