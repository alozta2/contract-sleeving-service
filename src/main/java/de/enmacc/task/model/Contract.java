package de.enmacc.task.model;

import java.util.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "contract")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@JsonIgnore
	private boolean status;

	private Date transactionDate;

	private String aCompany;

	private String anotherCompany;

	@OneToOne
	@JoinColumn(name = "material_id")
	private Material material;

	private double amount;

	@Transient
	private double rate;

	@Transient
	private double cost;

	@Transient
	@JsonProperty("status")
	private String statusStr;

	public Contract() {
		this.status = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getaCompany() {
		return aCompany;
	}

	public void setaCompany(String aCompany) {
		this.aCompany = aCompany;
	}

	public String getAnotherCompany() {
		return anotherCompany;
	}

	public void setAnotherCompany(String anotherCompany) {
		this.anotherCompany = anotherCompany;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getCost() {
		return this.getMaterial().getPrice() * this.getAmount() * this.getRate();
	}

	/**
	 * Assume this is determined with some external service
	 * 
	 * @return Random fee between 1.0 and 1.25
	 */
	public double getRate() {
		Random r = new Random();
		double randomValue = 1 + (1.25 - 1) * r.nextDouble();
		return randomValue;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Contract)) {
			return false;
		} else {
			Contract c = (Contract) o;
			if (this.id == c.getId())
				return true;
			else
				return false;
		}
	}

	public String getStatusStr() {
		return status ? "active" : "passive";
	}

	/**
	 * Checks if given contract is valid.
	 * 
	 * @return Validation status.
	 */
	public boolean isValid() {
		if (this.aCompany != null && 
				this.anotherCompany != null && 
				this.material != null && 
				this.material.getName() != null &&
				this.getAmount() > 0) {
			return true;
		} else
			return false;
	}
}
