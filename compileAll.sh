#!/bin/bash

for i in $(ls); do
  if [[ -d $i ]] ; then
    cd  $i 
    if [[ -f gradlew ]] ; then
       bash gradlew assembleDebug > ../../$i.compile 
    fi
    cd ..
  fi
done


