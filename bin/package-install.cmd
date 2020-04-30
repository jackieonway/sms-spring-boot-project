@echo off

echo project is packaging and installing ....
cd ..
mvn clean package install -Dmeven.test.skip=true
echo project is installed
cd ./bin