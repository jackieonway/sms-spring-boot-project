#!/bin/bash
version = $(cat updateVersion.txt)
echo 'Update sms spring boot starter project version to $version'
cd ..
mvn versions:set -DnewVersion=$version
echo 'Update sms spring boot starter version to $version success'
echo 'Update sms spring boot starter child modules version to $version'
mvn versions:update-child-modules
echo 'Update sms spring boot starter child modules version to $version success'