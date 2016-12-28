package hr.codiraona.IssueTicketing.backend.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hr.codiraona.IssueTicketing.backend.model.Ticket;
import hr.codiraona.IssueTicketing.backend.model.User;

@Stateless
public class TicketDAO {

	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@PersistenceContext(unitName = "IssueTicketing-backend")
	protected EntityManager em;
	
	public boolean createNewTicket(Ticket ticket){

		em.persist(ticket);
		return true;
	}
	
	/**
	 * Return false if asignee doesn't exist
	 * Return true if ticket has been succesfully assigned
	 * @param ticket
	 * @param username
	 * @return
	 */
	public boolean assignTicket(Ticket ticket, String username){
		User assignee = em.createNamedQuery("User.findByUsername", User.class)
				.setParameter("inUsername", username)
				.getSingleResult();
		
		if (assignee!=null){
			ticket.setAssignedTo(username);
			em.merge(ticket);
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Get all tickets
	 * @return
	 */
	public List<Ticket> getAllTickets(){
		
		return em.createNamedQuery("Ticket.findAll", Ticket.class).getResultList();
		
	}
	
	/**
	 * Updates ticket
	 * @param ticket
	 * @return
	 */
	public boolean updateTicket(Ticket ticket){
		em.merge(ticket);
		return true;
	}
	
	
	
}
