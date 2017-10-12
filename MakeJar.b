#!/bin/bash
cd class
jar cfm EquiPo.jar ../src/manifest.txt *.class 
mv EquiPo.jar ../
cd ..
