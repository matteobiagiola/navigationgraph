package graph;

import utils.Randomness;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphParser {

	/* -(\w*)- source*/
	/* (\w*) method*/
	/* -(\w*)\s target*/
	//edge syntax = methodName-sourceVertex-targetVertex hyphen
	public static String getMethodName(String edge){
		StringBuffer method = new StringBuffer();
		Pattern pattern = Pattern.compile("(\\w*)");
		Matcher matcher = pattern.matcher(edge);
		if(matcher.find()){
			method.append(matcher.group(1)); //take text between delimiters
		}
		return method.toString();
	}
	
	public static String getEdgeSource(String edge){
		StringBuffer sourceNode = new StringBuffer();
		Pattern pattern = Pattern.compile("-(\\w*)-");
		Matcher matcher = pattern.matcher(edge);
		if(matcher.find()){
			sourceNode.append(matcher.group(1));
		}
		return sourceNode.toString();
	}
	
	public static String getEdgeTarget(String edge){
		StringBuffer targetNode = new StringBuffer();
		Pattern pattern = Pattern.compile("-(\\w*)$");
		Matcher matcher = pattern.matcher(edge);
		if(matcher.find()){
			targetNode.append(matcher.group(1)); //take text between delimiters
		}
		return targetNode.toString();
	}
	
	public static List<String> getNodesFromEdges(List<String> edgeList, GraphPath<String,String> graphPath){
        if (edgeList.isEmpty()) {
            String startVertex = graphPath.getStartVertex();
            if (startVertex != null && startVertex.equals(graphPath.getEndVertex())) {
                return Collections.singletonList(startVertex);
            } else {
                return Collections.emptyList();
            }
        }

        Graph<String, String> g = graphPath.getGraph();
        List<String> list = new ArrayList<String>();
        String v = graphPath.getStartVertex();
        list.add(v);
        for (String e : edgeList) {
            v = Graphs.getOppositeVertex(g, e, v);
            list.add(v);
        }
        return list;
	}

	public static String fromMethodToEdge(String methodName, DirectedPseudograph<String,String> applicationGraph){
		List<String> edgesWithSameMethodName = new ArrayList<>();
		Set<String> edges = applicationGraph.edgeSet();
		for(String edge: edges){
			if(edge.contains(methodName)){
				edgesWithSameMethodName.add(edge);
			}
		}
		if(edgesWithSameMethodName.isEmpty()){
			throw new IllegalStateException("Application graph does not contain a valid edge for method " + methodName);
		}
		if(edgesWithSameMethodName.size() > 1){
			return Randomness.getInstance().choice(edgesWithSameMethodName);
		}
		return edgesWithSameMethodName.get(0);
	}
}
