package graph;

import org.jgrapht.ext.DOTImporter;
import org.jgrapht.ext.ImportException;
import org.jgrapht.graph.DirectedPseudograph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;


public class GraphImporter {

	private String pathToGraphFile;

	public GraphImporter(String pathToGraphFile){
		this.pathToGraphFile = pathToGraphFile;
	}
	
	public Optional<DirectedPseudograph<String,String>> importGraphDotFile(){
		DirectedPseudograph<String,String> graph = new DirectedPseudograph<>(String.class);
		NodesProvider nodesProvider = new NodesProvider();
		EdgesProvider edgesProvider = new EdgesProvider();
		DOTImporter<String, String> importer = new DOTImporter<String, String>(nodesProvider, edgesProvider);
		try {
			BufferedReader in = new BufferedReader(new FileReader(this.pathToGraphFile));
			importer.importGraph(graph, in);
			return Optional.of(graph);
		} catch (FileNotFoundException | ImportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
