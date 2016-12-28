package hr.codiraona.IssueTicketing.backend.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSPasswordCredential;

import hr.codiraona.IssueTicketing.backend.dao.CompanyDAO;
import hr.codiraona.IssueTicketing.backend.dao.UserDAO;
import hr.codiraona.IssueTicketing.backend.facade.CompanyFacade;
import hr.codiraona.IssueTicketing.backend.model.Company;
import hr.codiraona.IssueTicketing.backend.model.User;

@Stateless
public class CompanyBean implements CompanyFacade {

	@EJB
	private CompanyDAO companyDAO;
	
	@EJB
	private UserDAO userDAO;
	
	@Override
	public String createNewCompany(Company company) {

		if (companyDAO.createCompany(company)){
			return String.format("Company %s has been successfully created", company.getName());
		}
		else{
			return String.format("Company hasn't been succesfully created", company.getName());
		}
	}

	@Override
	public String updateCompany(Company company) {
		if (companyDAO.updateCompany(company)){
			return String.format("Company %s has been successfully updated", company.getName());
		}
		else{
			return String.format("Company %s has not been updated", company.getName());
		}
		
	}

	@Override
	public String deleteCompany(Company company) {
		
		List<User> users = company.getUsers();
		for(User user:users){
			userDAO.removeUser(user.getId());
		}
		
		if (companyDAO.removeCompany(company)){
			return "Company and coresponding users have been deleted";
		}
		
		else{
			return "Company haven't been deleted";
		}
		
	}

	@Override
	public List<Company> getAllCompanies() {
		
		return companyDAO.getAllCompanies();
			
	}

}
