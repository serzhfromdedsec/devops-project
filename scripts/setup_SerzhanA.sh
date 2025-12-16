#!/bin/bash

sudo apt update
sudo apt upgrade -y

sudo apt install -y openjdk-17-jdk gradle git

java -version
gradle -v
git --version
