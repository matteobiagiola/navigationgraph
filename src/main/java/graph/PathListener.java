package graph;

import graph.event.EdgeTraversalEvent;
import graph.event.TraversalListenerAdapter;

import java.util.ArrayList;
import java.util.List;


public class PathListener extends TraversalListenerAdapter<String, String> {
	
	List<String> edges = new ArrayList<String>();
	
	@Override
	public void edgeTraversed(EdgeTraversalEvent<String> e)
	{
		String edge = e.getEdge();
		this.edges.add(edge);
	}
	
	public List<String> getEdges(){
		return this.edges;
	}

}
