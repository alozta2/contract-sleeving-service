package de.enmacc.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.enmacc.task.model.Contract;

public class Node {
	private String companyName;
	private Contract contract;
	@JsonIgnore						//to avoid stack overflow during jackson serialization
	private List<Node> neighbours;

	Node(Contract contract)
	{
		this.contract = contract;
		this.companyName = contract.getaCompany();
		this.neighbours=new ArrayList<>();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Node) )
			return false;
		
		Node n = (Node) o;
		if(this.companyName == n.getCompanyName())
			return true;
		else
			return false;
	}
	
	public void addNeighbours(Node neighbourNode)
	{
		this.neighbours.add(neighbourNode);
	}
	
	public List<Node> getNeighbours() {
		return neighbours;
	}
	
	public void setNeighbours(List<Node> neighbours) {
		this.neighbours = neighbours;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
}
