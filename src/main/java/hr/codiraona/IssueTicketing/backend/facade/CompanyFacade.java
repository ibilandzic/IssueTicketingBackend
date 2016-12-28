package hr.codiraona.IssueTicketing.backend.facade;

import java.util.List;

import javax.ejb.Local;

import hr.codiraona.IssueTicketing.backend.model.Company;

@Local
public interface CompanyFacade {

	String createNewCompany(Company company);
	
	String updateCompany(Company company);
	
	String deleteCompany(Company company);
	
	List<Company> getAllCompanies();
	
	
	
}
