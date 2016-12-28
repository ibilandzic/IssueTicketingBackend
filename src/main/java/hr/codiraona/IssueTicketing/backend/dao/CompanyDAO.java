package hr.codiraona.IssueTicketing.backend.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import hr.codiraona.IssueTicketing.backend.model.Company;
import hr.codiraona.IssueTicketing.backend.model.User;
import hr.codiraona.IssueTicketing.backend.utils.BackendUtils;

@Stateless
@LocalBean
public class CompanyDAO{

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@PersistenceContext(unitName = "IssueTicketing-backend")
	protected EntityManager em;

	/**
	 * Creates new company
	 * @param company
	 * @return
	 */
	public boolean createCompany(Company company) {
			em.persist(company);
			return true;
	}
	
	/**
	 * Updates company
	 * @param company
	 * @return
	 */
	public boolean updateCompany(Company company){
			em.merge(company);
			return true;

	}
	
	/**
	 * Removes company
	 * @param company
	 * @return
	 */
	public boolean removeCompany(Company company){
		em.remove(company);
		return true;
	}
	
	public List<Company> getAllCompanies(){
		return em.createNamedQuery("Company.findAll", Company.class).getResultList();
	}

}
