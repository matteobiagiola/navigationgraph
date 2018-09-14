package graph;

import org.jgrapht.ext.VertexProvider;

import java.util.Map;

public class NodesProvider implements VertexProvider<String>{

	public String buildVertex(String label, Map<String, String> attributes) {
		// TODO Auto-generated method stub
		return label;
	}

}