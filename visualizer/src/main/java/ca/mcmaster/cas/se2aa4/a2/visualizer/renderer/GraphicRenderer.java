package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;


public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas, Configuration config) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas, config);
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas, Configuration config) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
        makeCities(config, aMesh, canvas);
    }

    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) { //I think hull contains all vertices in a given polygon p
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath(); //draws the segment that closes the polygon??
        canvas.draw(path); //draws path on canvas using current stroke settings (line width/style)
        
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList()); //extracts colour property and stores it in fill object (may be null if there is no colour??)
        if(fill.isPresent()) { //checks if fill contains a valid colour value
            Color old = canvas.getColor(); //stores current colour of canvas
            canvas.setColor(fill.get()); //sets the colour to fill value
            canvas.fill(path); //fills the shape defined by path with fill colour
            canvas.setColor(old); //restoring colour of canvas value
        }
        
    
    }
    public static void makeCities(Configuration config, Mesh aMesh, Graphics2D canvas) {
        LinkedHashSet<Vertex> landVertices = new LinkedHashSet<>();
        String cityCountStr = config.cityCount();
        int cityCount = Integer.parseInt(cityCountStr);
        List<Segment> segmentsList = aMesh.getSegmentsList();

       // List<Structs.Vertex> vertices = aMesh.getVerticesList();
        for (Polygon i : aMesh.getPolygonsList()) {
            List<Integer> segIdxs = i.getSegmentIdxsList();
            for (Property j : i.getPropertiesList()) {
                if (j.getKey().equals("rgb_color") && (j.getValue().equals("252,252,252"))) {
                    for (int s : segIdxs) {
                        Structs.Segment segment = segmentsList.get(s);
                        Vertex v1 = aMesh.getVertices(segment.getV1Idx());
                        Vertex v2 = aMesh.getVertices(segment.getV2Idx());
                        landVertices.add(v1);
                        landVertices.add(v2);
                    }
                }
            }
        }
        List<Vertex> landVerticesList = new ArrayList<>(landVertices);
        int verticesCount = landVerticesList.size(); //length of vertices list

        List<Integer> cityIdxs = new ArrayList<>();
        for (int i = 0 ; i < cityCount ; i++) {
            cityIdxs.add(randInt(0, verticesCount)); //list of elements for vertices to be cities
        }
        canvas.setColor(Color.PINK);
        for (int i = 0 ; i < cityIdxs.size() ; i++) {
            double x = landVerticesList.get(i).getX();
            double y = landVerticesList.get(i).getY();
            Ellipse2D circle = new Ellipse2D.Float((float) x-1.5f, (float) y-1.5f,6, 6);
            canvas.fill(circle);
        }
        
    } 
    //this methods finds a random number between a range
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public Mesh weightProperty(Mesh aMesh) {
        Random random = new Random();
        Mesh.Builder newMesh = Mesh.newBuilder();
        newMesh.addAllVertices(aMesh.getVerticesList());
        newMesh.addAllPolygons(aMesh.getPolygonsList());
        for (Segment s : aMesh.getSegmentsList()) {
            int segmentWeight = random.nextInt(101);
            String segmentWeightString = Integer.toString(segmentWeight);
            Structs.Property weight = Structs.Property.newBuilder().setKey("weight").setValue(segmentWeightString).build();
            Segment.Builder newSegment = s.toBuilder().addProperties(weight);
            newMesh.addSegments(newSegment);
        }
        return newMesh.build();
    }

}
