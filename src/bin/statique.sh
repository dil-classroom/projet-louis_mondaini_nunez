#!/bin/sh
DIR="$( cd "$(dirname "$0")" ; pwd -P )"
java -cp "$DIR/../lib/*" ch.heigvd.statique.Statique "$@"