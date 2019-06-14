package de.enmacc.task.model;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="material")
public class Material {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;
    
    private double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Random price changes simulates real life pricing scenario as prices can go higher and lower.
	 * */
	public double getPrice() {
		Random r = new Random();
		double randomValue = 0.9 + (1.1 - 0.9) * r.nextDouble();
		return price * randomValue;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
