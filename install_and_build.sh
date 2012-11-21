#!/bin/bash
sudo apt-get install openjdk-6-jre
sudo apt-get install openjdk-6-jdk
sudo apt-get install ant
ant -f buildfiles/buildMud.xml
