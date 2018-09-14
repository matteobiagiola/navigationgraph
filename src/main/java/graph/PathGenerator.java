package graph;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.List;

public class PathGenerator {

	/* the procedure stops when it reaches a node without outgoing edges
	 * if start node and target node are the same a limit has to be set otherwise the iterator never stops.
	 * */
	public List<String> generateRandomPath(DirectedPseudograph<String,String> graph, String startNodeGraph, int maxSteps){
		
		GraphIterator<String, String> iterator = new RandomWalkIterator<String, String>(graph, startNodeGraph, false, maxSteps);
		PathListener pathListener = new PathListener();
		iterator.addTraversalListener(pathListener);
		while(iterator.hasNext()){
			iterator.next();
		}
		List<String> edges = pathListener.getEdges();
		return edges;
	}
	
	/**
	 * @param target must be an edge of the graph
	 * */
	public List<String> generateRandomPathWithTarget(DirectedPseudograph<String,String> graph, String startNodeGraph, String target, int maxSteps){
		GraphIterator<String, String> iterator = new RandomWalkIterator<>(graph, startNodeGraph, target, false, maxSteps);
		PathListener pathListener = new PathListener();
		iterator.addTraversalListener(pathListener);
		while(iterator.hasNext()){
			iterator.next();
		}
		List<String> edges = pathListener.getEdges();

		// It may happen that with the maxSteps provided the random walk in the navigation graph
		// does not cover the desired target. In that case the shortest path between the last edge
		// and target is added at the end of the path.
		String lastEdge = edges.get(edges.size() - 1);
		if(edges.size() == maxSteps && !lastEdge.equals(target)){
			List<String> shortestPath = this.shortestPath(graph, lastEdge, target, edges);
			edges.addAll(shortestPath);
		}

		lastEdge = edges.get(edges.size() - 1);
		if(!lastEdge.equals(target)){
			throw new IllegalStateException("PathGenerator - generatePathWithRandomTarget: last edge of random path " +
					"within navigation graph " + lastEdge + " must be equal to target to cover " + target);
		}
		return edges;
	}

	private List<String> shortestPath(DirectedPseudograph<String,String> graph, String lastEdge,
									String edgeToAdd, List<String> edges){
		if(edges.size() == 0){
			throw new IllegalStateException("It was not possible to add the edge " + edgeToAdd + " because its starting node is not reachable from any node of the graph");
		}
		String startNode = GraphParser.getEdgeTarget(lastEdge);
		String targetNode = GraphParser.getEdgeSource(edgeToAdd);
		List<String> shortestPath = DijkstraShortestPath.findPathBetween(graph, startNode, targetNode);
		if(shortestPath != null){
			shortestPath.add(edgeToAdd);
		}else{
			throw new UnsupportedOperationException("shortestPath: shortestPath is null, not able to find a path from "
					+ startNode + "->" + targetNode);
		}
		return shortestPath;
	}
}

