package de.enmacc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.enmacc.task.model.Material;
import de.enmacc.task.repository.MaterialRepository;

@Service
public class MaterialService implements IMaterialService {

	private Logger logger = LoggerFactory.getLogger(ContractService.class);
	
	@Autowired
	private MaterialRepository repository;

	/**
	 * Gets database representation of given material
	 * @param materialName
	 * @return Material with given name.
	 * @throws IllegalArgumentException on no matching material
	 * */
	@Override
	public Material getMaterial(String materialName) throws IllegalArgumentException {
		Material material = repository.findByName(materialName);
		if(material == null) {
			String msg = "Material " + materialName + " does not exist.";
			logger.warn(msg);
			throw new IllegalArgumentException(msg);
		}
		return material;
	}
}
