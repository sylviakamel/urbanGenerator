public class impFindPath implements findPath {

	private GraphADT graph; //the graph I will use to find the shortest path

	//constructor 
	public impFindPath(GraphADT graph) { 
		this.graph = graph;
	}

	//method for finding the shortest path between 2 nodes that returns list of weighted edges
	//the shortest path is the path with the smallest weight sum
	public ArrayList<Edge<Node>> shortestPath(Node startNode, Node endNode) {
		ArrayList<Edge<Node>> shortestPath = new ArrayList<Edge<Node>>();
		Node currentNode = startNode;
		while (currentNode != endNode) {
			List neighbours = getNeighbourEdges(startNode); //list of edges connecting to startNode
			Edge smallestWeight = neighbours[0].getWeight();
			for (int i = 1 ; i < neighbours.size() ; i++) {
				if (neighbours[i].getWeight() < smallestWeight) {
					smallestWeight = neighbours[i].getWeight(); 
				}
			}
			//now I have a smallest weight value, add it to my output list
			shortestPath.add(neighbours[i]);
			//move to the next node
			currentNode = neigbours[i].getEndNode();
		}
		return shortestPath;
	}
}