package de.enmacc.utils.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.enmacc.utils.Node;

/**
 * Sleeve wrapper for List<Node> to represent return values more appealing.
 * */
public class SleeveWrapper {

	private List<String> sleevePath;
	private double totalCost;
	@JsonIgnore						//to avoid stack overflow while serialization
	private List<Node> sleeves;

	public SleeveWrapper(List<Node> sleeves)
	{
		this.sleevePath = new ArrayList<String>();
		if(sleeves != null) {
			this.sleeves = sleeves;
			for(Node n : sleeves) {
				sleevePath.add(n.getCompanyName());
				totalCost += n.getContract().getCost();
			}
		}
	}

	public List<String> getSleevePath() {
		return sleevePath;
	}

	public void setSleevePath(List<String> sleevePath) {
		this.sleevePath = sleevePath;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public List<Node> getSleeves() {
		return sleeves;
	}

	public void setSleeves(List<Node> sleeves) {
		this.sleeves = sleeves;
	}
}
