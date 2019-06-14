package de.enmacc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.enmacc.task.model.Contract;

public class Graph {
	private Logger logger = LoggerFactory.getLogger(Graph.class);
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private Set<String> companyNames;	//to keep track of company name information

	public Graph() {
        this.companyNames = new HashSet<String>();
    }

	/**
	 * Breadth first search algorithm implementation for graph data structure.
	 * 
	 * @param startNode Starting node of graph
	 * @param endNode Target node of graph
	 * @return All possible acyclic paths between given nodes.
	 * */
    public List<List<Node>> breadthFirstSearch(Node startNode, Node endNode) {
    	List<List<Node>> sleeves = new ArrayList<List<Node>>();
    	Queue<List<Node>> queue = new LinkedList<List<Node>>();
    	List<Node> tmpPath = new ArrayList<Node>();

    	tmpPath.add(startNode);
    	queue.add(tmpPath);
    	
    	logger.debug("\nBFS:");
    	while(!queue.isEmpty()) {
    		tmpPath = queue.poll();
    		Node lastNode = tmpPath.get(tmpPath.size()-1);
    		logger.debug("\n>>>" + listToString(System.identityHashCode(tmpPath)+"", tmpPath));
    		
    		if(lastNode.equals(endNode)) {
    	    	logger.debug("\n>>>" + "PATH: " + listToString(System.identityHashCode(tmpPath)+"", tmpPath));
    	    	sleeves.add(tmpPath);
    		}
    		List<Node> neighbours = lastNode.getNeighbours();
    		for(Node n : neighbours) {
    			if(!tmpPath.contains(n)) {
    				List<Node> new_path = new ArrayList<Node>();
    				new_path.addAll(tmpPath);
    				new_path.add(n);
    				queue.add(new_path);
    			}
    		}
    	}
    	return sleeves;
    }
    
    private String listToString(String name, List<Node> list) {
    	StringBuilder s = new StringBuilder("List#" + name + ":\n");
    	for(Node n : list) {
    		s.append(n.getCompanyName() + "\n");
    	}
    	return s.toString();
    }
    
    /**
     * Adds contract to existing graph.
     * 
     * @param contract A new contract
     * @return A contract wrapped inside of Node object
     * */
    public Node addNode(Contract contract) {
    	Node node = null;
    	if(contract == null) {
    		return node;
    	} else if(!companyNames.contains(contract.getaCompany())) {
    		companyNames.add(contract.getaCompany());
    		node = new Node(contract);
    		nodes.add(node);
    	} else {
    		node = getNode(contract.getaCompany());
    	}
    	return node;
    }
    
    public Node getNode(String companyName) {
    	for(Node n : nodes) {
    		if(n.getCompanyName().equals(companyName))
    			return n;
    	}
    	return null;
    }
    
    public void printGraph(){
    	StringBuilder s = new StringBuilder("\n");
        for(Node n : nodes) {
        	s.append(n.getCompanyName() + " neighbours: \n");
        	for(Node neighbour : n.getNeighbours()) {
        		s.append("  " + neighbour.getCompanyName() + "\n");
        	}
        }
        logger.debug(s.toString());
    }

	public Set<String> getCompanyNames() {
		return companyNames;
	}

	public void setCompanyNames(Set<String> companyNames) {
		this.companyNames = companyNames;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
}
