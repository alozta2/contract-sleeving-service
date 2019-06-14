package de.enmacc.service;

import de.enmacc.task.model.Material;

/**
 * Interface for MaterialService
 * */
public interface IMaterialService {

	public Material getMaterial(String materialName) throws IllegalArgumentException;
}
