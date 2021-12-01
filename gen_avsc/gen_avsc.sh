#!/bin/sh

SCRIPT_DIR="$(dirname "$0")"
cd "$SCRIPT_DIR" || exit

./avro-tools.sh idl2schemata ./avro_record.avdl ../src/main/avro/com/fillmore_labs/avro_demo/specific
