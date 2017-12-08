#!/usr/bin/env bash
java -jar ~/Documents/intellij-community/tools/lexer/jflex-1.7.0.jar --skel ~/Documents/intellij-community/tools/lexer/idea-flex.skeleton --nobak ResLexer.flex -d ./../../../../../gen/com/jetbrains/resolve/lexer/
