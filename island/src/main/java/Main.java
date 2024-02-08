import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

import configuration.Configuration;
import features.Clone;
import features.Mvp;

public class Main {

    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args); //reading command line
        Structs.Mesh aMesh = new MeshFactory().read(config.input()); //creates a mesh called aMesh that is equal to the mesh from the input (the -i part in the command line)
        System.out.println("test test Right before checking input");
        
        if(config.export().containsKey(Configuration.MODE)) { //if we find -mode + __ in the command line, run the following code
            System.out.println("Print this statement out if you type -mode in the command line!!! yay!!"); 
        }

        Mvp test = new Mvp();
        Vertex maxPoints = test.findMax(aMesh);
        Vertex centerXY = test.findCanvasCenter(aMesh, maxPoints);
        
        Double outerRadius = (centerXY.getY() / 2) + (centerXY.getY() / 4);
        Double innerRadius = (centerXY.getY() / 3);
        System.out.println("Printing out radius: " + outerRadius);
        Clone cloneMesh = new Clone();
        Mesh cloned = cloneMesh.circle(aMesh, outerRadius, innerRadius);
        new MeshFactory().write(cloned, config.export(Configuration.OUTPUT)); //writes to aMesh and outputs a file with the naming convention in Configuration.OUTPUT (in our case lagoon.mesh)
    }
}
