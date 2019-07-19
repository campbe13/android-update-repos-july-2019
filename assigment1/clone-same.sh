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
for i in $(cat ~/droid/samelist.txt)
do
   if [[ $odd == y ]] ; then
        repo=$i
        odd=n
   else
	dir2=$i 
        odd=y
	echo cloning $repo into $dir
        git clone $repo $dir2 2>&1 >/dev/null
   fi
done
