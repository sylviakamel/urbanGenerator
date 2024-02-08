package features;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

public class Mvp {

    public Vertex findMax(Mesh aMesh){
        double maxX = 0;
        double maxY = 0;
        double tempX = 0;
        double tempY = 0;

        for(Structs.Vertex v : aMesh.getVerticesList()) { //iterate through all vertices in the mesh
            tempX = v.getX(); //set tempX and tempY to the x and y coordinate of the vertex
            tempY = v.getY();
            if (tempX > maxX) //if a greater x value is found, set it to the maxX value
                maxX = tempX; //unsure if the hashset is in increasing order, otherwise use last index??
            if (tempY > maxY)
                maxY = tempY;
        }

        Structs.Vertex max = Structs.Vertex.newBuilder().setX(maxX).setY(maxY).build();
        return max;
    }

    public Vertex findCanvasCenter(Mesh aMesh, Vertex max){
        double centerX = max.getX() / 2; //divided the max x value by 2 to get the center values
        double centerY = max.getY() / 2;
        Structs.Vertex center = Structs.Vertex.newBuilder().setX(centerX).setY(centerY).build(); //building center vertex
        System.out.println("The center of the canvas is " + center);
        return center;
    }

    public double distanceToCenter(Vertex centerofCanvas, Vertex point) {
        double centerX = centerofCanvas.getX();
        double centerY = centerofCanvas.getY();

        double pointX = point.getX();
        double pointY = point.getY();

        double distance = Math.sqrt( Math.pow((pointX - centerX),2) + Math.pow((pointY - centerY),2) );

        return distance;        
    }
}
