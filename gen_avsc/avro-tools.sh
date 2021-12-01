#!/bin/sh

AVRO_VERSION="1.11.0"
LIB_DIR=".lib"

AVRO_TOOLS="avro-tools-$AVRO_VERSION.jar"
AVRO_URL="https://dlcdn.apache.org/avro/avro-$AVRO_VERSION/java/$AVRO_TOOLS"

[ -d "$LIB_DIR" ] || mkdir "$LIB_DIR"

[ -f "$LIB_DIR/$AVRO_TOOLS" ] || curl -Lo "$LIB_DIR/$AVRO_TOOLS" "$AVRO_URL"

shasum -a 512 -c - << _SHASUM || exit 1
fd61212a170ff7f91b76f7f3b894feb93e90768d7dcd726a335729355bda04d36d6bee41845bea41fb2f17b0b769fbb473d62caa18b16f20089af1bb035a34fb *$LIB_DIR/$AVRO_TOOLS
_SHASUM

java -jar "$LIB_DIR/$AVRO_TOOLS" "$@"
