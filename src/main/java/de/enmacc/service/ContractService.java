package de.enmacc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.enmacc.task.model.Contract;
import de.enmacc.task.model.Material;
import de.enmacc.task.repository.ContractRepository;
import de.enmacc.utils.Graph;
import de.enmacc.utils.Node;

@Service
public class ContractService implements IContractService {
	private Logger logger = LoggerFactory.getLogger(ContractService.class);
	
	@Autowired
	private ContractRepository repository;
	
	@Autowired
	private IMaterialService materialService;

	@Override
	public List<Contract> findAll() {
		
		List<Contract> contracts = new ArrayList<Contract>();
		contracts = (List<Contract>) repository.findAll();
		
		return contracts;
	}

	@Override
	public List<Contract> findByACompany(String aCompany) {

		List<Contract> contracts = new ArrayList<Contract>();
		if(aCompany != null)
			contracts = repository.findByACompany(aCompany);
		
		return contracts;
	}

	@Override
	public List<Contract> findByAnotherCompany(String anotherCompany) {

		List<Contract> contracts = new ArrayList<Contract>();
		if(anotherCompany != null)
			contracts = repository.findByAnotherCompany(anotherCompany);
		
		return contracts;
	}

	@Override
	public List<Contract> findByACompanyOrAnotherCompany(String aCompany, String anotherCompany) {

		List<Contract> contracts = new ArrayList<Contract>();
		if(aCompany != null && anotherCompany != null)
			contracts = repository.findByACompanyOrAnotherCompany(aCompany, anotherCompany);
		
		return contracts;
	}

	@Override
	public List<Contract> findByACompanyAndAnotherCompany(String aCompany, String anotherCompany) {

		List<Contract> contracts = new ArrayList<Contract>();
		if(aCompany != null && anotherCompany != null)
			contracts = repository.findByACompanyAndAnotherCompany(aCompany, anotherCompany);
		
		return contracts;
	}

	@Override
	public List<Contract> findByACompanyOrAnotherCompanyOrderByTransactionDateDesc(String aCompany, String anotherCompany) {

		List<Contract> contracts = new ArrayList<Contract>();
		if(aCompany != null && anotherCompany != null)
			contracts = repository.findByACompanyOrAnotherCompanyOrderByTransactionDateDesc(aCompany, anotherCompany);
		
		return contracts;
	}
	
	@Override
	public List<Contract> findByACompanyOrderByTransactionDateDescLimitN(String aCompany, Integer limit) {
		
		List<Contract> contracts = new ArrayList<Contract>();
		if(aCompany != null && limit != null)
			contracts = repository.findByACompanyOrderByTransactionDateDescLimitN(aCompany, limit);
		
		return contracts;
	}

	/**
	 * Calculates sleeves based on the contracts stored on database.
	 * 
	 * @param aCompany A company
	 * @param anotherCompany  company
	 * @return list of sleeves that each has company names in each node
	 * */
	@Override
	public List<List<Node>> getSleeves(String aCompany, String anotherCompany) {
		List<List<Node>> sleeveNodes = new ArrayList<List<Node>>();
		try {
			List<Contract> allContracts = (List<Contract>) repository.findAll();
			Graph graph = initializeGraph(allContracts);
			Node n1 = graph.getNode(aCompany);
			Node n2 = graph.getNode(anotherCompany);
			if(n1 != null && n2 != null) {
				sleeveNodes = graph.breadthFirstSearch(n1, n2);
			}
		} catch(Exception e) {
			logger.error("Error occured while converting contracts into Graph: " + e);
		}

		return sleeveNodes;
	}
	
	/**
	 * Initializes a graph data structure for given Contract list.
	 * Graph is chosen to represent contract records on the database in memory.
	 * With this solution I'm able to calculate relation with each other such as
	 * sleeves.
	 * 
	 * Downside: Performance issues on bigger data.
	 * 
	 * @param allContracts Contract list
	 * @return Graph representation of existing contracts
	 * */
	private Graph initializeGraph(List<Contract> allContracts) throws Exception {
		logger.debug("Initializing a graph for sleeve calculation.");
        Graph graph = new Graph();
		
		for(Contract c : allContracts) {
			Node nodeACompany = graph.addNode(c);				//add a contract with direction in a graph
			String tmp = c.getaCompany();
			c.setaCompany(c.getAnotherCompany());
			c.setAnotherCompany(tmp);							//change direction so that trade is mutual
			Node nodeAnotherCompany = graph.addNode(c);			//nothing happens if they already exist
			nodeACompany.addNeighbours(nodeAnotherCompany);
			nodeAnotherCompany.addNeighbours(nodeACompany);
		}
		graph.printGraph();
		
		return graph;
	}

	/**
	 * Checks if the given contract is valid.
	 * 
	 * @param contract Potential new contract
	 * @return Database copy of new contract
	 * @throws IllegalArgumentException on invalid contracts.
	 * */
	@Override
	public Contract save(Contract contract) throws IllegalArgumentException {
		if(contract.isValid() && !hasDuplicateContract(contract)) {
			contract.setTransactionDate(new Date());
			Material material = materialService.getMaterial(contract.getMaterial().getName());
			contract.setMaterial(material);
			return repository.save(contract);
		} else {
			throw new IllegalArgumentException("Contract is not valid.");
		}
	}
	
	/**
	 * Checks database if the given contract is already exist.
	 * PS1: I assumed that there can be only one contract between two companies.
	 * PS2: company_1 -> company_2 and company_2 -> company_1 contracts are assumed to be the same thing.
	 * 
	 * @param contract
	 * @return Duplicate status of contract.
	 * */
	private boolean hasDuplicateContract(Contract contract) {
		List<Contract> c1 = findByACompanyAndAnotherCompany(contract.getaCompany(), contract.getAnotherCompany());
		List<Contract> c2 = findByACompanyAndAnotherCompany(contract.getAnotherCompany(), contract.getaCompany());
		
		if(c1.size() == 0 && c2.size() == 0)
			return false;
		else
			return true;
	}
}
