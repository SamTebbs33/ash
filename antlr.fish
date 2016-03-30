set CLASSPATH ".:lib/antlr-4.5.3-complete.jar" 
java -Xmx500M -cp $CLASSPATH org.antlr.v4.Tool Ash.g -o src/ash/grammar/antlr -package ash.grammar.antlr -visitor -no-listener
rm src/ash/grammar/antlr/AshBaseVisitor.java
#rm src/ash/grammar/antlr/*Listener.java
