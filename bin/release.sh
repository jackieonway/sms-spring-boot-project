#!/bin/bash

echo 'project is releasing ....'
cd ..
mvn clean package deploy -Dmeven.test.skip=true
echo 'project is released'
cd ./bin