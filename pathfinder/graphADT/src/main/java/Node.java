public class Node {
	private string name; //name of city
	private int elevation; //elevation fo city

	//creating a constructor for class Node
	public Node(String name, int elevation) {
		this.name = name;
		this.elevation = elevation;
	}

	//name getter
	public String getName() {
		return name;
	}
	
	//elevation getter
	public int getElevation() {
		return elevation;
	}

	//name setter
	public void setName(String name) {
		this.name = name;
	}

	//elevation setter
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}
}