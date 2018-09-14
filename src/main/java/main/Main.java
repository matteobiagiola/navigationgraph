package main;

import graph.GraphImporter;
import graph.GraphParser;
import graph.PathGenerator;
import org.jgrapht.graph.DirectedPseudograph;
import utils.Randomness;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Main {

    public static void main(String[] args){

        // Import navigation graph
        String currentUserDirectory = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator"); // in Unix '/', in Windows '\'
        String mavenDirectoryStructure = "src" + fileSeparator + "main";
        String pathToGraphFile = currentUserDirectory + fileSeparator + mavenDirectoryStructure
                + fileSeparator + "resources" + fileSeparator + "graphs"
                + fileSeparator + "phoenix.txt";
        System.out.println("Navigation graph absolute path: " + pathToGraphFile);
        GraphImporter graphImporter = new GraphImporter(pathToGraphFile);
        Optional<DirectedPseudograph<String, String>> optionalNavigationGraph = graphImporter.importGraphDotFile();
        DirectedPseudograph<String, String> navigationGraph;
        if(optionalNavigationGraph.isPresent()){
            navigationGraph = optionalNavigationGraph.get();
        }else{
            throw new IllegalStateException("");
        }

        // Use navigation graph
        String startNodeGraph = "LoginContainerPage"; // put in java properties
        int maxLengthOfPath = 40; // put in java properties (max length of test case without considering input values)
        Set<String> edgeSet = navigationGraph.edgeSet();
        String randomNavigationMethod = Randomness.getInstance().choice(edgeSet); // choose randomly a navigation method
        // Syntax of navigation method in navigation graph: methodName-sourceVertex-targetVertex
        System.out.println("Navigation method to cover: " + randomNavigationMethod);
        System.out.println("Navigation method name: " + GraphParser.getMethodName(randomNavigationMethod));
        System.out.println("Navigation method source vertex: " + GraphParser.getEdgeSource(randomNavigationMethod));
        System.out.println("Navigation method target vertex: " + GraphParser.getEdgeTarget(randomNavigationMethod));
        PathGenerator pathGenerator = new PathGenerator();
        List<String> listOfNavigationMethods = pathGenerator.generateRandomPathWithTarget(navigationGraph, startNodeGraph,
                randomNavigationMethod, maxLengthOfPath);
        System.out.println("List of navigation methods: " + listOfNavigationMethods + ". Navigation method "
                + randomNavigationMethod + " should be the last in the list.");
    }
}
