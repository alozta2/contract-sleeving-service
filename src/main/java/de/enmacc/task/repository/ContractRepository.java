package de.enmacc.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.enmacc.task.model.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

	//The following methods describe the keywords supported for JPA and automatically translated into sql queries
	public List<Contract> findByACompany(String aCompany);
	public List<Contract> findByAnotherCompany(String anotherCompany);
	public List<Contract> findByACompanyOrAnotherCompany(String aCompany, String anotherCompany);
	public List<Contract> findByACompanyAndAnotherCompany(String aCompany, String anotherCompany);
	public List<Contract> findByACompanyOrAnotherCompanyOrderByTransactionDateDesc(String aCompany, String anotherCompany);
	
	//Parametric query demonstration
	@Query(value = "select * "
			+ "from contract "
			+ "where a_company = :aCompany or another_company = :aCompany "
			+ "order by transaction_date desc "
			+ "limit :limit", nativeQuery=true)
	public List<Contract> findByACompanyOrderByTransactionDateDescLimitN(String aCompany, Integer limit);
}
