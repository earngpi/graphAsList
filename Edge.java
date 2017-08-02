
public class Edge<T extends Comparable<T>> {
	
	private Vertex<T> vert1; 
	private Vertex<T> vert2; //vertices connected by this edge
	private int weight;
	
	public Edge(Vertex<T> v1, Vertex<T> v2, int w){
		vert1 = v1;
		vert2 = v2;
		weight = w;
	}
	
	public void display(){
		System.out.print("<"+vert1.getValue()+"-"+vert2.getValue()+">"+"("+weight+")");
	}
	
	public Vertex<T> getVt1(){
		return vert1;
	}
	
	public Vertex<T> getVt2(){
		return vert2;
	}
	
	public int getWeight(){
		return weight;
	}
}
