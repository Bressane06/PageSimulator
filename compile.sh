#!/bin/bash

set -e

mkdir -p target/classes

sources=$(find src/main/java -name "*.java")

if [ -z "$sources" ]; then
  echo "Nenhum arquivo Java encontrado em src/main/java."
  exit 0
fi

javac -encoding UTF-8 -d target/classes $sources
