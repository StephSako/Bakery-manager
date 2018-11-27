#!/bin/bash
javac -d logger/bin logger/src/logger/*.java
javac -cp "logger/bin" -d restaurant/bin restaurant/src/restaurant/*.java
javac -cp "restaurant/bin:testframework/lib/*:logger/bin:" -d testframework/bin testframework/src/test/*.java

cd testframework/bin
java -cp "../../restaurant/bin:../lib/junit3.8.1.jar:../../logger/bin:" junit.textui.test.RunTest -junit3 test.TestNoteClient
java -cp "../../restaurant/bin:../lib/junit3.8.1.jar:../../logger/bin:" junit.textui.test.RunTest -junit3 test.TestRestaurant
