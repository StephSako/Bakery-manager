#!/bin/bash
javac -d logger/bin logger/src/logger/*.java && javac -d restaurant/bin -cp "logger/bin" restaurant/src/restaurant/*.java
