#!/bin/sh
tags="-wB"
def="\e[37m"
red="\e[31m"
lb="\e[94m"
lc="\e[96m"
gr="\e[32m"
ly="\e[93m"
lg="\e[37m"
function runtest () {
  # 1 Title
  # 2 fileName no extension
  # 3 maxScore
  rm files/*
  echo "Running $1 Test\n========================================="
  java Warehouse < inputs/$2.in >  outputs/$2.out
  if diff $tags "expectedOutputs/$2.out"  "outputs/$2.out" &> /dev/null ; then
    score=$3;
    printf "$1:$gr Passed$lc\n"
  else
      printf "$1:$red Failed$lc\n"
    score=0;
    diff $tags "expectedOutputs/$2.out"  "outputs/$2.out"
  fi
  printf "Score: $lg$score$lc/$ly$3$lc\n"
  echo "=========================================\n\n"

}


printf "$lc=============================\n"
printf "|Project 5:$red Amazon Warehouse$lc|\n"
echo "|        Testall            |"
echo "============================="
echo "Cleaning...\n"
rm *.class &> /dev/null #remove class files
rm files/*  &> /dev/null 
rm -rf outputs &> /dev/null 
mkdir outputs

total=0;
echo "Compiling...\n\n"
javac *.java #recompile

runtest "Menu" "menu" 25
((total+=$score))
runtest "Add Package Menu" "addPackage" 10
((total+=$score))
runtest "Add Vehicle Menu" "addVehicle" 10
((total+=$score))
runtest "Prime Day Menu Update" "updateMenuPrimeDay" 10
((total+=$score))
runtest "Stats" "statisticsNoData" 10
((total+=$score))
runtest "Menu Error" "menuError" 8
((total+=$score))
runtest "Enter Vehicle Error" "vehErrors" 9
((total+=$score))
runtest "No Packages Available Error" "noPackagesAvailable" 9
((total+=$score))
runtest "No Vehicles Available Error" "noVehiclesAvailable" 9
((total+=$score))
totalScore=100
printf "$lc=============================\n"
printf "Total Score: $lg$total$lc/$ly$totalScore$lc"
printf "\n$lc=============================\n\n"

#clean up
rm *.class
rm files/*



