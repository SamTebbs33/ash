#!/usr/bin/env bash
rm -rf src/as/grammar/antlr/*
java -Xmx500M -cp $CLASSPATH org.antlr.v4.Tool Ash.g -o src/ash/grammar/antlr -package ash.grammar.antlr -visitor -no-listener
rm src/ash/grammar/antlr/AshBaseVisitor.java