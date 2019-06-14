package de.enmacc.service;

import java.util.List;

import de.enmacc.task.model.Contract;
import de.enmacc.utils.Node;

/**
 * Interface for ContractService
 * */
public interface IContractService {

	public List<Contract> findAll();
	public List<Contract> findByACompany(String aCompany);
	public List<Contract> findByAnotherCompany(String anotherCompany);
	public List<Contract> findByACompanyOrAnotherCompany(String aCompany, String anotherCompany);
	public List<Contract> findByACompanyAndAnotherCompany(String aCompany, String anotherCompany);
	public List<Contract> findByACompanyOrAnotherCompanyOrderByTransactionDateDesc(String aCompany, String anotherCompany);
	public List<List<Node>> getSleeves(String aCompany, String anotherCompany);
	public Contract save(Contract contract) throws IllegalArgumentException;
	public List<Contract> findByACompanyOrderByTransactionDateDescLimitN(String aCompany, Integer limit);
}
