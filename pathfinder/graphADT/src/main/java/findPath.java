package features;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;


//interface to define public contract for finding a path between two nodes
public interface findPath {
	List<Vertex> findPath(Vertex start, Vertex end, Mesh aMesh);
}