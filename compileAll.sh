#!/bin/bash
# compile all directories
# 2019-07-19
if [[ ! -d compiles ]] ; then
   mkdir compiles
fi

for i in $(ls); do
  if [[ -d $i ]] ; then
    cd  $i 
    if [[ -f gradlew ]] ; then
       bash gradlew assembleDebug > ../compiles/$i.compile  2>../compiles/$i.error
    fi
    cd ..
  fi
done


