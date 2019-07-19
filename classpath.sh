#!/bin/bash
# fix app level build.gradle
# 2019-07-18

for i in $(cat list.dirs); do
   cd $i 
   cp build.gradle build.gradle.$(date +%F) 
   cp ../calculator-04/build.gradle .
   cd ..
done

