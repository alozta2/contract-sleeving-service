package de.enmacc.task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.enmacc.task.model.Material;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Long>  {

	public Material findByName(String name);
}
