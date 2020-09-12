#!/bin/bash

rm -rf ../../../Tools/glassfish-5.0.1/glassfish5/glassfish/domains/domain1/autodeploy/ear-1.0-SNAPSHOT.ear*
cp ear/target/ear-1.0-SNAPSHOT.ear ../../../Tools/glassfish-5.0.1/glassfish5/glassfish/domains/domain1/autodeploy/
