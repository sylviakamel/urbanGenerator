public class Edge {
	private int weight; //will use to find the shortest path
	private Node startNode;
	private Node endNode;

	//creating a constructor for class Edge
	public Edge(Node startNode, Node endNode, int weight) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.weight = weight;
	}

	//weight getter
	public int getWeight() {
		return weight;
	}

	//start node getter
	public Node getStartNode() {
		return startNode;
	}

	//end node getter
	public Node getEndNode() {
		return endNode;
	}

	//start node setter
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	//end node setter
	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	//weight setter
	public void setWeight(int weight) {
		this.weight = weight;
	}
}