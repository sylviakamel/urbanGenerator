#!/bin/bash

mvn install
java -jar generator/generator.jar -k irregular -h 1080 -w 1920 -p 3000 -s 20 -o img/input.mesh 
java -jar island/island.jar -i img/input.mesh -o img/lagoon.mesh -mode lagoon 
java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/island.svg -cityCount 500

#testing
#java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/island.svg -cityCount -100
#java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/island.svg -cityCount 0
#java -jar visualizer/visualizer.jar -i img/lagoon.mesh -o img/island.svg -cityCount 1000