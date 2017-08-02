import java.util.List;
import java.util.ArrayList;

/*
 * All methods return graph, list of vertices or list of edges that point to totally different locations to their inputs.
 * This is so that further computations of both the input and output graphs won't lead to data inconsistency.
 */

public class GraphApp<T extends Comparable<T>> {
	
//depth-first search-------------------------------------------------------------------------	
	/*
	 * Simulates stack dfs using list with LIFO. 
	 */
	public Graph<T> dfs(Graph<T> g){
		//declaration
		Graph<T> result = new Graph<T>(); //keeps result graph to be returned
		List<Vertex<T>> tos = new ArrayList<Vertex<T>>(); //stack abstraction, stores actual vertices from "input" graph
		List<Vertex<T>> tmp = new ArrayList<Vertex<T>>(); //similar to tos, stores vertices(created from input graph) to be added to "output" result
		
		//initialisation
		tos.add(g.getVertex(0));
		Vertex<T> init = new Vertex<T>(g.getVertex(0).getValue());
		tmp.add(init);
		
		//looping until finish(tos is empty)
		while(!tos.isEmpty()){
			Vertex<T> vt1 = tmp.remove(tmp.size()-1); 
			Vertex<T> rep = tos.remove(tos.size()-1);
			result.addVertex(vt1);
			for(int i=0; i<=rep.getEdgeList().size()-1; i++){ //get neighbors of current vertex and add to tmp and tos if appropriate
				if(!tos.contains(rep.getEdgeList().get(i)) 
						&& !result.checkVertex(rep.getEdgeList().get(i).getValue())){ //if the vertex is already in stack or transversed(already in result) then skip it
					Vertex<T> vt2 = new Vertex<T>(rep.getEdgeList().get(i).getValue());
					tos.add(rep.getEdgeList().get(i));
					tmp.add(vt2);
					result.addEdge(vt1, vt2, (g.getEdge(rep, rep.getEdgeList().get(i))).getWeight()); //add edges from current vertex to added(tmp,tos) neighbors to result graph
				}
			}
		}
		return result;
	}
	
	
	
//breadth-first search--------------------------------------------------------------------------------------	
	/*
	 * Simulates queue bfs using list with FIFO. 
	 */
	public Graph<T> bfs(Graph<T> g){
		//declaration
		Graph<T> result = new Graph<T>(); //keeps result graph to be returned
		List<Vertex<T>> queue = new ArrayList<Vertex<T>>(); //queue abstraction, stores actual vertices from "input" graph
		List<Vertex<T>> tmp = new ArrayList<Vertex<T>>(); //similar to queue, stores vertices(created from input graph) to be added to "output" result
		
		//initialisation
	    queue.add(g.getVertex(0));
		Vertex<T> init = new Vertex<T>(g.getVertex(0).getValue());
		tmp.add(init);
		
		//looping until finish(queue is empty)
		while(!queue.isEmpty()){
			Vertex<T> vt1 = tmp.remove(0);
			Vertex<T> rep = queue.remove(0);
			result.addVertex(vt1);
			for(int i=0; i<=rep.getEdgeList().size()-1; i++){ //get neighbors of current vertex and add to tmp and queue if appropriate
				if(!queue.contains(rep.getEdgeList().get(i)) 
						&& !result.checkVertex(rep.getEdgeList().get(i).getValue())){ //if the vertex is already in stack or transversed(already in result) then skip it
					Vertex<T> vt2 = new Vertex<T>(rep.getEdgeList().get(i).getValue());
					queue.add(rep.getEdgeList().get(i));
					tmp.add(vt2);
					result.addEdge(vt1, vt2, 
							(g.getEdge(rep, rep.getEdgeList().get(i))).getWeight()); //add edges from current vertex to added(tmp,queue) neighbors to result graph
				}
			}
		}
		return result;
	}
	
	
	
//Prim's algorithm--------------------------------------------------------------------------------------	
	/*
	 * A simple Prim's for MST.
	 * Initialise the MST with first vertex in V. In each loop, the code finds edges that connect 
	 * vertices already inside the MST to the one outside. Then, among those edges, find the one
	 * with minimal weight and add it to the result MST. Looping continues until MST includes all vertices.
	 */
	public Graph<T> primMST(Graph<T> g){
		//declaration and initialisation
		Graph<T> result = new Graph<T>(); //keeps result graph to be returned
		result.addVertex(g.getVertex(0).getValue());
		
		//looping until result MST contains all vertices
		while(result.getV().size()!=g.getV().size()){
			List<Edge<T>> tmp = new ArrayList<Edge<T>>(); //stores the edges connecting vertices inside and outside the "current" result MST
			
			for(int i=0; i<=g.getE().size()-1; i++){
				if( (result.checkVertex(g.getEdge(i).getVt1().getValue()) 
					&& !result.checkVertex(g.getEdge(i).getVt2().getValue())) 
						|| 
						(!result.checkVertex(g.getEdge(i).getVt1().getValue()) 
						&& result.checkVertex(g.getEdge(i).getVt2().getValue())) ){ //add the edge to tmp if it connects a vertex already inside the result MST to the one outside
					tmp.add(g.getEdge(i));
				}
			}
			
			Edge<T> min = tmp.get(0); 
			for(int i=1; i<=tmp.size()-1; i++){
				if(min.getWeight() > tmp.get(i).getWeight()){ //from tmp, find the edge with minimum weight
					min = tmp.get(i);
				}
			}
			
			if(!result.checkVertex(min.getVt1().getValue())){ //add the minimum edge and the vertex(of the edge) that was not in the result MST
				Vertex<T> newVt = new Vertex<T>(min.getVt1().getValue());
				result.addVertex(newVt);
			    result.addEdge(newVt, result.getVertex(min.getVt2().getValue()), 
			    		min.getWeight());
			}else if(!result.checkVertex(min.getVt2().getValue())){
				Vertex<T> newVt = new Vertex<T>(min.getVt2().getValue());
				result.addVertex(newVt);
				result.addEdge(result.getVertex(min.getVt1().getValue()), newVt, 
						min.getWeight());
			}
		}
		return result;
	}
	

	
//Kruskal's algorithm--------------------------------------------------------------------------------		
	/*
	 * A simple Kruskal's for MST
	 * The edges are sorted in ascending order. Each loop adds the minimum edge "if" the edge 
	 * does not create cycle in the result MST. Looping continues until MST includes all vertices.
	 */
	public Graph<T> kruskalMST(Graph<T> g){
		//declaration
		Graph<T> result = new Graph<T>(); //keeps final result to be returned
		List<Edge<T>> sortedE = g.sortEdge(); //sorted in increasing order, keeps unprocessed edges
		
		//looping until result MST contains all vertices
		while(result.getE().size()!=g.getV().size()-1){
			Edge<T> current = sortedE.remove(0); //get first/smallest weight edge
			//name useful values for later uses
			boolean checkV1 = result.checkVertex(current.getVt1().getValue());
			boolean checkV2 = result.checkVertex(current.getVt2().getValue());
			Vertex<T> vt1 = new Vertex<T>(current.getVt1().getValue());
			Vertex<T> vt2 = new Vertex<T>(current.getVt2().getValue());
			
		    if(!checkV1 && !checkV2){ //when result MST contains neither the vertices connected by the current edge
		    	result.addVertex(vt1); //add vertices first, then the edge to the result MST
		    	result.addVertex(vt2);
		    	result.addEdge(vt1, vt2, current.getWeight());
		    	//if neither vertices were in the MST, then there no way adding the edge will create a cycle
		    	
		   
		    }else if(checkV1 && !checkV2){ //when result MST contains only one of the vertices connected by the current edge
		    	result.addVertex(vt2); //add the missing vertex, then the edge to the result MST
		    	result.addEdge(result.getVertex(vt1.getValue()), vt2, 
		    			current.getWeight());
		    	if(isCycle(result)==true){ //however, if adding the edge creates a cycle in the graph, remove it since MST should not be cyclic
		    		result.deleteVertex(vt2);
		    	}
		    }else if(!checkV1 && checkV2){ //when result MST contains only one of the vertices connected by the current edge
		    	result.addVertex(vt1); //add the missing vertex, then the edge to the result MST
		    	result.addEdge(vt1, result.getVertex(vt2.getValue()), 
		    			current.getWeight());
		    	if(isCycle(result)==true){ //however, if adding the edge creates a cycle in the graph, remove it since MST should not be cyclic
		    		result.deleteVertex(vt1);
		    	}
		    }else if(checkV1 && checkV2){ //when result MST contains both vertices connected by the current edge
		    	result.addEdge(result.getVertex(vt1.getValue()), //then, just add the edge
		    			result.getVertex(vt2.getValue()), current.getWeight());
		    	if(isCycle(result)==true){ //however, if adding the edge creates a cycle in the graph, remove it since MST should not be cyclic
		    		result.deleteEdge(result.getE().size()-1);
		    	}
		    }
		}
		return result;
	}
	
	
	
//Dijkstra's algorithm--------------------------------------------------------------------------------
	
	/*
	 * The upcoming code implements Dijkstra's that return "Single-Source Shortest Path" graph.
	 * Each vertex are evaluated such that all of its edges have been considered. Upon evaluating the
	 * last vertex, the shortest path from source to each vertex should be found. Then, these info
	 * are used to create the result graph.
	 */
	public Graph<T> dijkstraSSSP(Graph<T> g, Vertex<T> source){
		//declaration
		Graph<T> result = new Graph<T>(); //keeps result to be returned
		List<Vertex<T>> tmp = new ArrayList<Vertex<T>>(); //keeps unprocessed vertices
		List<Vertex<T>> predecessor = new ArrayList<Vertex<T>>(); //stores predecessor of each vertex
		int[] pathW = new int[g.getV().size()]; //stores path weight from source to each vertex
		
		//initialisation
		int evalCount = 0;
		tmp.add(source);
		for(int i=0; i<=g.getV().size()-1; i++){ //add all vertices to result graph
			result.addVertex(g.getVertex(i).getValue());
		}
		for(int i=0; i<=pathW.length-1; i++){ //set path weight from source with maximal value except for the source itself, should be 0
			if(i==g.getV().indexOf(source)){
				pathW[i] = 0;
			}else{
				pathW[i] = Integer.MAX_VALUE;
			}
		}
		for(int i=0; i<=g.getV().size()-1; i++){ //set predecessor of each vertex to null
			predecessor.add(new Vertex<T>(null));
		}
		
		//looping until all vertices have been evaluated/transversed
		while(evalCount!=g.getV().size()){
			Vertex<T> toEval = tmp.remove(0);
			for(int i=0; i<=toEval.getEdgeList().size()-1; i++){ //from every edge that connects vertexToBeEvaluated to others
				Vertex<T> vt = toEval.getEdgeList().get(i); //get the neighboring vertex of the vertexToBeEvaluated
				if(!tmp.contains(vt)){ //if the neighboring vertex of the vertexToBeEvaluated is not yet in tmp, add it there
					tmp.add(vt);
				}
				int weight = g.getEdge(toEval, vt).getWeight()+pathW[g.getV().indexOf(toEval)]; //"total" weight from source to vt if vertexToBeEvaluated is its predecessor 
				int minW = pathW[g.getV().indexOf(vt)]; //"current" weight from source to vt as previously stored
				if(weight<minW){ //if using the vertexToBeEvaluated as predecessor reduce the path weight from source to vt
					pathW[g.getV().indexOf(vt)] = weight; //update path weight and predecessor of vt
					predecessor.remove(g.getV().indexOf(vt));
					predecessor.add(g.getV().indexOf(vt), result.getVertex(toEval.getValue()));
				}
			}
			evalCount++;
		}
		
		//using values from predecessor list, add the edges to the result graph
		for(int i=0; i<=result.getV().size()-1; i++){
			Vertex<T> vt1 = predecessor.get(i); 
			Vertex<T> vt2 = result.getVertex(i); //set vt1 and vt2 such that vt1 is the predecessor of vt2
			if(vt1.getValue()!=null){
				int w = g.getEdge(g.getVertex(vt1.getValue()), g.getVertex(vt2.getValue())).getWeight();
				result.addEdge(vt1, vt2, w);
			}
		}
		return result;
	}
	
	
	
	/*
	 * The upcoming code implements Dijkstra's that solves the "Shortest Path Problem".
	 * Using the graph returned by the above method greatly simplifies the code. The returned graph
	 * is designed such that for all edges, vt1 is the predecessor of vt2. This info is used
	 * here to reach the source from sink.
	 */
	public List<Edge<T>> dijkstraSPP(Graph<T> g, Vertex<T> source, Vertex<T> sink){
		//declaration and initialisation
		List<Edge<T>> shortPath = new ArrayList<Edge<T>>(); //keeps result to be returned
		Graph<T> dijkOfG = dijkstraSSSP(g, source); //using the above method to simplify the computation
		Vertex<T> predecessor = dijkOfG.getVertex(sink.getValue()); //initialise predecessor vertex as the sink
		
		//looping until the source is reached(predecessor is the source)
		while(predecessor.getValue()!=source.getValue()){
			for(int i=0; i<=dijkOfG.getE().size()-1; i++){ //starting from sink, keep updating predecessor until reaching source
				Edge<T> current = dijkOfG.getEdge(i);
				if(current.getVt2()==predecessor){
					shortPath.add(0, current);
					predecessor = current.getVt1(); //remember from the above method that vt1 of edge is always the predecessor of v2
				}
			}
		}
		return shortPath;
	}
	
	
	
//helper methods-------------------------------------------------------------------------------------
	public boolean isCycle(Graph<T> g){
		Graph<T> dfsOfG = dfs(g);
		if(isConnected(g)){
			if(g.getE().size()!=dfsOfG.getE().size()){
				return true;
			}
		}else{
			Graph<T> disconG = new Graph<T>();
			for(int i=0; i<=g.getE().size()-1; i++){
				if(!dfsOfG.checkVertex(g.getE().get(i).getVt1().getValue())){
					Edge<T> current = g.getEdge(i);
					Vertex<T> vt1 = current.getVt1();
					Vertex<T> vt2 = current.getVt2();
					if(!disconG.checkVertex(vt1.getValue())){
						disconG.addVertex(vt1.getValue());
					}
					if(!disconG.checkVertex(vt2.getValue())){
						disconG.addVertex(vt2.getValue());
					}
					disconG.addEdge(disconG.getVertex(vt1.getValue()), disconG.getVertex(vt2.getValue()), g.getEdge(vt1, vt2).getWeight());
				}
			}
			Graph<T> dfsOfDiscon = dfs(disconG);
			if(g.getE().size()!=dfsOfG.getE().size()+dfsOfDiscon.getE().size()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isConnected(Graph<T> g){
		Graph<T> dfsOfG = dfs(g);
		if(dfsOfG.getV().size()==g.getV().size()){
			return true;
		}
		return false;
	}
	
	public static void SOPln(Object o){
		System.out.println(o);
	}
	
	public static void SOP(Object o){
		System.out.print(o);
	}

}
