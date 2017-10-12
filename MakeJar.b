#!/bin/bash
cd class
jar cfm EquiPo.jar ../src/manifest.txt *.class res
mv EquiPo.jar ../
cd ..
