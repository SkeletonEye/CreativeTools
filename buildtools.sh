#!/bin/sh

rm -rf buildtools
mkdir buildtools
cd buildtools
curl "https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar" -o BuildTools.jar
java -jar BuildTools.jar
cd ..
