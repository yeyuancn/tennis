#!/bin/bash
clear

echo --------------------------
echo Start Tennis Master build
echo --------------------------


mvn clean install
echo --------------------------
echo DONE WITH Build Tennis
echo --------------------------

echo --------------------------
echo Deploy the WAR files
echo --------------------------


rm -fr /Users/Yuan/Development/apache-tomcat-8.0.33/webapps/*
cp target/*.war /Users/Yuan/Development/apache-tomcat-8.0.33/webapps/

echo --------------------------
echo Moved WAR FILE for Tennis
echo --------------------------

echo --------------------------
echo DONE WITH DEPLOY TASK
echo --------------------------


