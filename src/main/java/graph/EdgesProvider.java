package graph;

import org.jgrapht.ext.EdgeProvider;

import java.util.Map;

public class EdgesProvider implements EdgeProvider<String,String>{


	public String buildEdge(String from, String to, String label, Map<String, String> attributes) {
		// TODO Auto-generated method stub
		int indexParenthesis = label.indexOf("(");
		String labelWithoutParenthesis = label.substring(0, indexParenthesis);
		String edge = labelWithoutParenthesis + "-" + from + "-" + to;
		return edge;
	}

}