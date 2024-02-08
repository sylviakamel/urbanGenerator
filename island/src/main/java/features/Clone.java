package features;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property.Builder;
import configuration.Configuration;
import features.Clone;

public class Clone {
    public Mesh oval(Mesh aMesh, double outerRadius, double innerRadius) {
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(aMesh.getVerticesList());
        clone.addAllSegments(aMesh.getSegmentsList());
        String ocean = "4" + "," + "84" + "," + "140";
        String land = "252" + "," + "252" + "," + "252";
        String lagoon = "100" + "," + "180" + "," + "208";
        String beach = "252" + "," + "255" + "," + "202";
    
        Mvp test = new Mvp();
        Vertex maxPoints = test.findMax(aMesh);
        Vertex centerXY = test.findCanvasCenter(aMesh, maxPoints);
    
        double WidthOval = outerRadius * 2;
        double HeightOval = innerRadius * 2;
        double X = centerXY.getX() - (WidthOval / 2);
        double Y = centerXY.getY() - (HeightOval / 2);
    
        for (Structs.Polygon poly : aMesh.getPolygonsList()) {
            Vertex centroid = aMesh.getVertices(poly.getCentroidIdx());
            Double distance = test.distanceToCenter(centerXY, centroid);
    
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(poly);
    
            // Ensure the polygon is inside the oval
            double deltaX = centroid.getX() - centerXY.getX();
            double deltaY = centroid.getY() - centerXY.getY();
            double RadiusX = WidthOval / 2;
            double RadiusY = HeightOval / 2;
            
            if (((deltaX * deltaX) / (RadiusX * RadiusX)) + ((deltaY * deltaY) / (RadiusY * RadiusY)) <= 1) { //Checks if polygon is within oval shape
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(land)
                        .build();
                pc.addProperties(p);
            } 
            else {//If not, it is an ocean tile
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(ocean)
                        .build();
                pc.addProperties(p);
            }
            clone.addPolygons(pc);
        }
        return clone.build();
    }
    
    public Mesh circle(Mesh aMesh, double outerRadius, double innerRadius) {
        Set<Polygon> b_polygons = new LinkedHashSet<>();
        Structs.Mesh.Builder clone = Structs.Mesh.newBuilder();
        clone.addAllVertices(aMesh.getVerticesList());
        clone.addAllSegments(aMesh.getSegmentsList());
        String ocean = "4" + "," + "84" + "," + "140";
        String land = "252" + "," + "252" + "," + "252";
        String lagoon = "100" + "," + "180" + "," + "208";
        String red = "219" + "," + "112" + "," + "147";
        String beach = "252" + "," + "255" + "," + "202";

        Mvp test = new Mvp();
        Vertex maxPoints = test.findMax(aMesh);
        Vertex centerXY = test.findCanvasCenter(aMesh, maxPoints);
    
        for(Structs.Polygon poly: aMesh.getPolygonsList()) {
            Vertex centroid = aMesh.getVertices(poly.getCentroidIdx());
            Double distance = test.distanceToCenter(centerXY, centroid);

            //System.out.println("Printing out centroids" + centroid);
            //System.out.println("Printing distance" + distance);
            Structs.Polygon.Builder pc = Structs.Polygon.newBuilder(poly);
            
            if (distance <= innerRadius){
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(lagoon)
                        .build();
                pc.addProperties(p);
            }
            else if (distance >= innerRadius && distance <= outerRadius) {
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(land)
                        .build();
                pc.addProperties(p);
                b_polygons.add(poly);
                }
            else {
                Structs.Property p = Structs.Property.newBuilder()
                        .setKey("rgb_color")
                        .setValue(ocean)
                        .build();
                pc.addProperties(p);
                    
                }
                clone.addPolygons(pc);
        }
        for (Polygon poly_1: b_polygons) {
            for(Integer neigbourIdx: poly_1.getNeighborIdxsList()){
                //access the neighbour using the getPolygons method
                Structs.Polygon neighbour = clone.getPolygons(neigbourIdx);
                //find the rgb property
                for (Property property : neighbour.getPropertiesList()) {    
                    if (property.getKey().equals("rgb_color") && property.getValue().equals(ocean)) {
                        Structs.Property p2 = Structs.Property.newBuilder()
                                .setKey("rgb_color")
                                .setValue(beach)
                                .build();
                        Structs.Polygon.Builder pb = Structs.Polygon.newBuilder(neighbour);
                        pb.addProperties(p2); // add beach property to neighbour polygon
                        clone.addPolygons(pb); // add neighbour polygon to clone
                    }
                    else if (property.getKey().equals("rgb_color") && property.getValue().equals(lagoon)) {
                        Structs.Property p2 = Structs.Property.newBuilder()
                                .setKey("rgb_color")
                                .setValue(beach)
                                .build();
                        Structs.Polygon.Builder pb = Structs.Polygon.newBuilder(neighbour);
                        pb.addProperties(p2); // add beach property to neighbour polygon
                        clone.addPolygons(pb); // add neighbour polygon to clone
                    }
                }
            }
        }
        return clone.build();
    }
    
    
}
