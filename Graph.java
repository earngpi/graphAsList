import java.util.List;
import java.util.ArrayList;

public class Graph<T extends Comparable<T>> {
	
	private List<Vertex<T>> vertices = new ArrayList<Vertex<T>>();
	private List<Edge<T>> edges = new ArrayList<Edge<T>>();
	
	public Graph(){ }
	public Graph(List<Vertex<T>> V, List<Edge<T>> E){
		addV(V);
		addE(E);
	}
	
	public List<Vertex<T>> getV(){
		return vertices;
	}
	
	public List<Edge<T>> getE(){
		return edges;
	}
	
	
	
//vertex manipulation---------------------------------------------------------------------
	public void addV(List<Vertex<T>> V){
		for(int i=0; i<=V.size()-1; i++){
			if(checkVertex(V.get(i).getValue())){
				V.remove(i);
				i--;
			}
		}
		vertices.addAll(V);
	}
	
	public void addVertex(Vertex<T> vertex){
		if(!checkVertex(vertex.getValue())){
			vertices.add(vertex);
		}
	}
	
	public void addVertex(T value){
		if(!checkVertex(value)){
			Vertex<T> vt = new Vertex<T>(value);
			vertices.add(vt);;
		}
	}
	
	public boolean checkVertex(Vertex<T> vertex){
		if(vertices.contains(vertex)){
			return true;
		}
		return false;
	}
	
	public boolean checkVertex(T value){
		for(int i=0; i<=vertices.size()-1; i++){
			if(getVertex(i).getValue()==value){
				return true;
			}
		}
		return false;
	}
	
	public Vertex<T> getVertex(int index){
		if(vertices.size()-1>=index){
			return vertices.get(index);
		}
		return null;
	}
	
	public Vertex<T> getVertex(T value){
		for(int i=0; i<=vertices.size()-1; i++){
			if(getVertex(i).getValue()==value){
				return getVertex(i);
			}
		}
		return null;
	}
	
	public Vertex<T> deleteVertex(Vertex<T> vertex){
		if(!vertices.contains(vertex)){
			return null;
		}
		for(int i=0; i<=edges.size()-1;i++){ //updating edge lists of other vertices connected to this vertex
			if(getEdge(i).getVt1()==vertex){
				getEdge(i).getVt2().getEdgeList().remove(vertex);
				deleteEdge(i);
				i--; //following the decrese in size of edge list
			}else if(getEdge(i).getVt2()==vertex){
				getEdge(i).getVt1().getEdgeList().remove(vertex);
				deleteEdge(i);
				i--; //following the decrese in size of edge list
			}
		}
		vertices.remove(vertex);
		return vertex;
	}
	
	public Vertex<T> deleteVertex(int index){
		if(vertices.size()-1<index){
			return null;
		}
		for(int i=0; i<=edges.size()-1;i++){ //updating edge lists of other vertices connected to this vertex
			if(getEdge(i).getVt1()==getVertex(index)){
				getEdge(i).getVt2().getEdgeList().remove(getVertex(index));
				deleteEdge(i); //following the decrese in size of edge list
				i--;
			}else if(getEdge(i).getVt2()==getVertex(index)){
				getEdge(i).getVt1().getEdgeList().remove(getVertex(index));
				deleteEdge(i);
				i--; //following the decrese in size of edge list
			}
		}
		return vertices.remove(index);
	}
	
	
	
//edge manipulation--------------------------------------------------------------------
	public void addE(List<Edge<T>> E){
		for(int i=0; i<=E.size()-1; i++){
			if(checkEdge(E.get(i).getVt1().getValue(), E.get(i).getVt2().getValue())){
				E.remove(i);
				i--;
			}
		}
		edges.addAll(E);
		for(int i=0; i<=E.size()-1; i++){
			E.get(i).getVt1().getEdgeList().add(E.get(i).getVt2());
			E.get(i).getVt2().getEdgeList().add(E.get(i).getVt1()); //updates edge lists of vertices
		}
	}
	
	public void addEdge(Edge<T> edge){
		if(!checkEdge(edge)){
			edges.add(edge);
			edge.getVt1().getEdgeList().add(edge.getVt2());
			edge.getVt2().getEdgeList().add(edge.getVt1()); //updates edge lists of both vertices
		}
	}
	
	public void addEdge(Vertex<T> vt1, Vertex<T> vt2, int w){
		if(!checkEdge(vt1.getValue(), vt2.getValue())){
			Edge<T> edge = new Edge<T>(vt1, vt2, w);
			edges.add(edge);
			edge.getVt1().getEdgeList().add(edge.getVt2());
			edge.getVt2().getEdgeList().add(edge.getVt1()); //updates edge lists of both vertices
		}
	}
	
	public boolean checkEdge(Edge<T> edge){
		if(edges.contains(edge)){
			return true;
		}
		return false;
	}
	
	public boolean checkEdge(Vertex<T> val1, Vertex<T> val2){
		for(int i=0; i<=edges.size()-1; i++){
			if(getEdge(i).getVt1()==val1 && getEdge(i).getVt2()==val2){
				return true;
			}else if(getEdge(i).getVt1()==val2 && getEdge(i).getVt2()==val1){ //since working with undirected graph
				return true;
			}
		}
		return false;
	}
	
	public boolean checkEdge(T v1, T v2){
		for(int i=0; i<=edges.size()-1; i++){
			if(getEdge(i).getVt1().getValue()==v1 && getEdge(i).getVt2().getValue()==v2){
				return true;
			}else if(getEdge(i).getVt1().getValue()==v2 && getEdge(i).getVt2().getValue()==v1){ //since working with undirected graph
				return true;
			}
		}
		return false;
	}
	
	public Edge<T> getEdge(int index){
		if(edges.size()-1<index){
			return edges.get(index);
		}
		return null;
	}
	
	public Edge<T> getEdge(Vertex<T> v1, Vertex<T> v2){
		for(int i=0; i<=edges.size()-1; i++){
			if(getEdge(i).getVt1()==v1 && getEdge(i).getVt2()==v2){
				return getEdge(i);
			}else if(getEdge(i).getVt1()==v2 && getEdge(i).getVt2()==v1){ //since undirected
				return getEdge(i);
			}
		}
		return null;
	}
	
	public Edge<T> deleteEdge(Edge<T> edge){
		if(!edges.contains(edge)){
			return null;
		}
		edge.getVt1().getEdgeList().remove(edge.getVt2());
		edge.getVt2().getEdgeList().remove(edge.getVt1()); //update edge lists of both vertices
		edges.remove(edge);
		return edge;
	}
	
	public Edge<T> deleteEdge(int index){
		if(edges.size()-1<index){
			return null;
		}
		getEdge(index).getVt1().getEdgeList().remove(getEdge(index).getVt2());
		getEdge(index).getVt2().getEdgeList().remove(getEdge(index).getVt1()); //update edge lists of both vertices
		return edges.remove(index);
	}
	
	public Edge<T> maxEdge(){
		Edge<T> max = edges.get(0);
		for(int i=1; i<=edges.size()-1; i++){
			if(max.getWeight() < getEdge(i).getWeight()){
				max = getEdge(i);
			}
		}
		return max;
	}
	
	public Edge<T> minEdge(){
		Edge<T> min = edges.get(0);
		for(int i=1; i<=edges.size()-1; i++){
			if(min.getWeight() > getEdge(i).getWeight()){
				min = getEdge(i);
			}
		}
		return min;
	}
	
	public List<Edge<T>> sortEdge(){
		List<Edge<T>> result = new ArrayList<Edge<T>>();
		Graph<T> tmp = new Graph<T>(vertices, edges);
		for(int i=0; i<=tmp.getE().size()-1; i++){
			Edge<T> min = tmp.minEdge();
			result.add(tmp.deleteEdge(min));
			i--;
		}
		return result;
	}

	
	
//helper methods-----------------------------------------------------------------------
	
	public static void SOPln(Object o){
		System.out.println(o);
	}
	
	public static void SOP(Object o){
		System.out.print(o);
	}
	
	public void displayV(){
		SOP("[ ");
		for(int i=0; i<=vertices.size()-1; i++){
			getVertex(i).display();
			if(i!=vertices.size()-1){
				SOP(", ");
			}
		}SOP(" ]");
		SOPln("");
	}
	
	public void displayE(){
		SOP("[ ");
		for(int i=0; i<=edges.size()-1; i++){
			getEdge(i).display();
			if(i!=edges.size()-1){
				SOP(", ");
			}
		}SOP(" ]");
		SOPln("");
	}
	
	public void displayEList(){
		for(int i=0; i<=vertices.size()-1; i++){
			vertices.get(i).displayEList();
		}
	}

}
