#!/bin/bash
#script to test api 
#http://www.edamam.com
#2014
#ID=e6501390
#KEY=71b785fe9091261dbe192db334d77dbd
#2015
ID=b3f7ba07
KEY=77397e154a68c54cb1380e268edffa84	
#test nutrition analysis api
#https://developer.edamam.com/nutrition-docs
curl -d @recipe.json -H "Content-Type: application/json" "https://api.edamam.com/api/nutrition-details?app_id=$ID&app_key=$KEY" >nutr.txt
#test recipe search api
#https://developer.edamam.com/recipe-docs
curl "https://api.edamam.com/search?q=chicken&app_id=$ID&app_key=$KEY" >recip.txt
#test diet recommendations api
#https://developer.edamam.com/diet_api_documentation
curl "https://api.edamam.com/diet?q=chicken&app_id=$ID&app_key=$KEY&from=0&to=3&calories=gte%20591,%20lte%20722&health=alcohol-free" >diet.txt



