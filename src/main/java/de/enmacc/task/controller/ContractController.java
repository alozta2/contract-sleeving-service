package de.enmacc.task.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.enmacc.service.IContractService;
import de.enmacc.task.model.Contract;
import de.enmacc.utils.Node;
import de.enmacc.utils.wrapper.ResponseWrapper;
import de.enmacc.utils.wrapper.SleeveWrapper;

@RestController
public class ContractController {
	Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Autowired
	IContractService contractService;

    /**
     * Persists the given contract in the database.
     * Only the following parameters will be accepted as input, others will be ignored.
     * aCompany, anotherCompany, material.name, amount
     *
     * @param contract The contract to persist
     * @return The persisted contract
     */
    @PostMapping(path = "/contracts", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper addContract(@RequestBody @NotNull Contract contract) {
    	String response = "Success";
    	Contract newContract = null;
    	try {
	    	if(contract != null && 
	    			contract.getaCompany() != null && 
	    			contract.getAnotherCompany() != null && 
	    			contract.getaCompany().equals(contract.getAnotherCompany())) {
				throw new IllegalArgumentException("A company cannot have contracts with itself.");
			}
    		newContract = contractService.save(contract);
    	} catch(IllegalArgumentException e1) {
    		response = e1.getMessage();
    		logger.error(response);
    		e1.printStackTrace();
    	} catch(Exception e2 ) {
    		response = "Error occured while saving contract to database.";
    		logger.error(response);
    		e2.printStackTrace();
    	}
    	
    	return new ResponseWrapper(Arrays.asList(newContract), response);
    }

    /**
     * Computes based on the contracts in the database all sleeves for the two given companies.
     *
     * @param aCompany A company
     * @param anotherCompany Another company
     * @param option Display options for sleeve list. default: cost descended, asc: cost ascended, best: retrieve only the best result
     * @return The list of sleeves. A sleeve is a list of contracts between the companies.
     */
    @GetMapping(path = "/sleeves")
    public ResponseWrapper getAllPossibleSleeves(
    		@PathParam(value="aCompany") String aCompany, 
    		@PathParam(value="anotherCompany") String anotherCompany,
    		@PathParam(value="option") String option) {
    	
    	String response = "Success";
    	List<SleeveWrapper> sleeveWrapper = new ArrayList<SleeveWrapper>();
    	try {
    		if(aCompany == null || anotherCompany == null) {
    			throw new IllegalStateException("Two companies have to be specified to calculate sleeves.");
    		} else if(aCompany.equals(anotherCompany)) {
    			throw new IllegalStateException("A company cannot have contracts with itself.");
    		}
	    	List<List<Node>> sleeves = contractService.getSleeves(aCompany, anotherCompany);
	    	for(List<Node> l : sleeves) {
	    		SleeveWrapper sw = new SleeveWrapper(l);
	    		sleeveWrapper.add(sw);
	    	}
	    	
	    	//retrieve sleeve list in various fashion
	    	if(option != null) {
	    		switch (option) {
				case "desc":
			    	Collections.sort(sleeveWrapper, (a,b) -> a.getTotalCost() < b.getTotalCost() ? 1 : 
						a.getTotalCost() == b.getTotalCost() ? 0 : -1);
					break;
				case "best":
			    	Collections.sort(sleeveWrapper, (a,b) -> a.getTotalCost() < b.getTotalCost() ? -1 : 
						a.getTotalCost() == b.getTotalCost() ? 0 : 1);
			    	SleeveWrapper bestSleeveOption = (sleeveWrapper != null && sleeveWrapper.size() > 0) ? 
			    										bestSleeveOption = sleeveWrapper.get(0) : null;
			    	sleeveWrapper = new ArrayList<SleeveWrapper>();
			    	if(bestSleeveOption != null)
			    		sleeveWrapper.add(bestSleeveOption);
					break;
				default:
			    	Collections.sort(sleeveWrapper, (a,b) -> a.getTotalCost() < b.getTotalCost() ? -1 : 
						a.getTotalCost() == b.getTotalCost() ? 0 : 1);
				}
	    	}
    	} catch(Exception e) {
    		logger.error("Error occured while processing /sleeves.");
    		e.printStackTrace();
    		response = e.getMessage();
    	}
    	
    	return new ResponseWrapper(sleeveWrapper, response);
    }

    /**
     * Get last N contracts of the company. 
     * List arrives descendingly sorted by their transaction date by default.
     *
     * @param aCompany A company
     * @return The list of contracts.
     */
    @GetMapping(path = "/history")
    public ResponseWrapper getLastNContractOfCompany(
    		@PathParam(value="aCompany") String aCompany,
    		@PathParam(value="limit") Integer limit) {

    	final int MAX_LIMIT = 20;
    	String response = "Success";
    	List<Contract> contracts = new ArrayList<Contract>();
    	try {
    		if(aCompany == null) {
    			throw new IllegalStateException("A company must be specified to retrieve its history.");
    		}
    		if(limit == null) {
    			contracts = contractService.findByACompanyOrAnotherCompanyOrderByTransactionDateDesc(aCompany, aCompany);
    			if(contracts == null || contracts.size() == 0)
    				throw new Exception("Company " + aCompany + " does not exist.");
    		} else if(limit <= 0 || limit > MAX_LIMIT ) {
    			throw new IllegalArgumentException("Illegal limit value: Acceptable limit values are [1," + MAX_LIMIT + "].");
    		} else {
    			//I could've use below method for both purposes but I wanted to add more flavors to the table anyway
    			contracts = contractService.findByACompanyOrderByTransactionDateDescLimitN(aCompany, limit);
    		}
    	} catch(Exception e) {
    		logger.error("Error occured while processing /history.");
    		e.printStackTrace();
    		response = e.getMessage();
    	}
    	
    	return new ResponseWrapper(contracts, response);
    }
}
