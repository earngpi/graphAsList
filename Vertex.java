import java.util.List;
import java.util.ArrayList;

public class Vertex<T extends Comparable<T>> {
	
	private T value;
	private List<Vertex<T>> edgeList = new ArrayList<Vertex<T>>(); //keeps edges from/to this vertex
	
	
	public Vertex(T input){
		value = input;
	}
	
	public void display(){
		System.out.print(value);
	}
	
	public void displayEList(){
		System.out.print(value+": ");
		for(int i=0; i<=edgeList.size()-1; i++){
			System.out.print(edgeList.get(i).getValue());
			if(i!=edgeList.size()-1){
				System.out.print(", ");
			}
		}System.out.println();
	}
	
	public T getValue(){
		return value;
	}
	
	public List<Vertex<T>> getEdgeList(){
		return edgeList;
	}
}
