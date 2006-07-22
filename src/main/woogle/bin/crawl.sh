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

CRAWL_HOME=`cd "$THIS_DIR/.." ; pwd`
CRAWL_BIN_DIR=$CRAWL_HOME/bin
CRAWL_CONF=$CRAWL_HOME/conf
CRAWL_DATA=$CRAWL_HOME/data
CRAWL_WORK_DIR=$CRAWL_HOME/work
CRAWL_LOG_DIR=$CRAWL_HOME/log

NUTCH_HOME=$CRAWL_HOME/lib/nutch
NUTCH_BIN=$NUTCH_HOME/bin/nutch
NUTCH_CONF_DIR=$CRAWL_CONF

export NUTCH_CONF_DIR=$NUTCH_CONF_DIR

URLS=$CRAWL_CONF/urls.txt

# Clean up before crawling
rm -rf $CRAWL_WORK_DIR/wicket
rm -rf $CRAWL_LOG_DIR/crawl.log

# Run the crawl
$NUTCH_BIN crawl $URLS -dir $CRAWL_WORK_DIR/wicket -depth 20 >& $CRAWL_LOG_DIR/crawl.log

# Move to the data dir
rm -rf $CRAWL_DATA/wicket.old
mv $CRAWL_DATA/wicket $CRAWL_DATA/wicket.old 2> /dev/null
mv $CRAWL_WORK_DIR/wicket $CRAWL_DATA/wicket

# Restart the app server
$CRAWL_BIN_DIR/shutdown.sh

$CRAWL_BIN_DIR/startup.sh
