#!/bin/bash
javac -d logger/bin logger/src/*.java
javac -cp "logger/bin" -d restaurant/bin restaurant/src/*.java
javac -cp "restaurant/bin:testframework/lib/*:logger/bin:" -d testframework/bin testframework/src/*.java

cd testframework/bin
java -cp "../../restaurant/bin:../lib/*:../../logger/bin:" junit.textui.TestRunner -junit3 TestNoteClient
java -cp "../../restaurant/bin:../lib/*:../../logger/bin:" junit.textui.TestRunner -junit3 TestRestaurant
