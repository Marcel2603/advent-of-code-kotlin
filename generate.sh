#!/bin/bash
SRC_DIR="$(pwd)/src"
#DAY=$(date +%d)
DAY=02
TMPL_FILE="$SRC_DIR/DayXX.kt"
DAY_TMPL="Day$DAY"
KOTLIN_FILE="$SRC_DIR/$DAY_TMPL.kt"

if [ -f $KOTLIN_FILE ]; then
  echo "File exists; doing nothing"
  exit 0
fi

cp $TMPL_FILE $KOTLIN_FILE
sed -i '' "s/XX/$DAY/g" $KOTLIN_FILE

touch "$SRC_DIR/$DAY_TMPL.txt"
touch "$SRC_DIR/${DAY_TMPL}_test.txt"
