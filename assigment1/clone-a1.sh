#!/bin/bash
cd ~/droid
dir=assign1-2018
if   [ ! -d $dir ]
then
mkdir $dir
fi
odd=y    
cd $dir
date +%F
for i in $(cat ../list.txt)
do
	echo cloning $i 
        git clone $i
done
