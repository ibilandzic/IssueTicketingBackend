package hr.codiraona.IssueTicketing.backend.dao;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hr.codiraona.IssueTicketing.backend.dto.UserDTO;
import hr.codiraona.IssueTicketing.backend.model.Message;
import hr.codiraona.IssueTicketing.backend.model.Ticket;
import hr.codiraona.IssueTicketing.backend.model.User;

/**
 * DAO for user operations
 * 
 * @author iva.bilandzic
 *
 */

@Stateless
public class UserDAO{

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@PersistenceContext(unitName = "IssueTicketing-backend")
	protected EntityManager em;


/**
 * For testing purposes
 * @param entityManager
 */
	public UserDAO(EntityManager entityManager){
		em = entityManager;
	}
	
	public UserDAO(){
		
	}
	/**
	 * Method to create new user. Input is User type with all fields
	 * 
	 * @param user
	 * @return
	 */
	public boolean createUser(User user) {
		try {
			em.persist(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warning("Unable to create new user");
			return false;
		}
	}

	/**
	 * Method to update user
	 * 
	 * @param user
	 * @return
	 */
	public boolean updateUser(UserDTO userDTO) {
		boolean isUpdated = false;
		try {
			User user = em.find(User.class, userDTO.getId());
			if (user != null) {
				setUserValues(user, userDTO);
				em.merge(user);
				isUpdated = true;
			}
			return isUpdated;

		} catch (Exception e) {
			e.getMessage();
			logger.warning("Unable to update user id:" + userDTO.getId());
			return false;
		}

	}

	private void setUserValues(User user, UserDTO userDTO) {
		user.setCompany(userDTO.getCompany());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setRole(userDTO.getRole());
		user.setUsername(userDTO.getUsername());
		user.setLocation(userDTO.getLocation());
	}

	/**
	 * Sets user new password
	 * 
	 * @param password
	 * @param user_id
	 * @return
	 */

	public boolean setPassword(String password, int user_id) {
		try {
			User user = em.find(User.class, user_id);
			user.setPassword(password);
			em.merge(user);
			return true;
		} catch (Exception e) {
			e.getMessage();
			logger.warning(e.getMessage());
			logger.warning("Unable to set pasword for user id: " + user_id);
			return false;
		}

	}
        
        

	/**
	 * Logs out user - remove token
	 * 
	 * @param id
	 * @return
	 */
	public boolean logOut(int id) {
		try {
			User user = em.find(User.class, id);
			user.setExpiresAt(null);
			user.setToken(null);
			em.merge(user);
			return true;
		} catch (Exception e) {
			logger.warning(e.getMessage());
			logger.warning("Unable to remove registration token for user id: " + id);
			return false;
		}
	}

	/**
	 * Logs in user - create token
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User logIn(String username, String password) {
		User user = null;
		try {
			TypedQuery<User> query = em.createNamedQuery("User.findByCredentials", User.class);
			query.setParameter("inUsername", username);
			query.setParameter("inPassword", password);
			user = query.getSingleResult();
			if (user != null) {
				user.setToken(generateToken());
				em.merge(user);
			}
			return user;
		} catch (Exception e) {
			logger.log(Level.WARNING, "User not found", e.getMessage());
			return null;
		}
	}

	/**
	 * Return tickets reported by specific user
	 * @param username
	 * @return
	 */

	public List<Ticket> getTicketsReportedByUser(String username) {
		try {
			TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByReported", Ticket.class);
			query.setParameter("inUsername", username);
			List<Ticket> tickets = query.getResultList();
			if (tickets != null) {
				return tickets;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unpossible to fetch tickets", e.getMessage());
			return null;
		}

	}
	

	/**
	 * Returns tickets assigned to specific user
	 * @param username
	 * @return
	 */
	public List<Ticket> getTicketAssignedToUser(String username) {
		try {
			TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAssignee", Ticket.class);
			query.setParameter("inUsername", username);
			List<Ticket> tickets = query.getResultList();
			if (tickets != null) {
				return tickets;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unpossible to fetch tickets", e.getMessage());
			return null;
		}
	}

	private String generateToken() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Remove specific user
	 * @param id
	 * @return
	 */
	public boolean removeUser(int id){
		try{
			//get user by id
			User user = em.find(User.class, id);
			//set assignedTo and reportedBy fields in tickets to null
			setAssignedToErased(user.getUsername());
			setReportedByToErased(user.getUsername());
			//set posted by field in message to username and erased
			setPostedByToErased(user.getUsername());
			em.remove(user);
			return true;
		}
		catch(Exception e){
			logger.warning(e.getMessage());
			logger.warning("Unable to delete user with id: "+id);
			return false;
		}
	}
	
	private void setPostedByToErased(String username){
		TypedQuery<Message> query = em.createNamedQuery("Message.findByAuthor", Message.class);
		query.setParameter("inUsername", username);
		List<Message> messages = query.getResultList();
		if (!messages.isEmpty()){
			for (Message message: messages){
				message.setPostedBy(String.format("%s-erased", username));
				em.merge(message);
			}
		}
	}
	
	private void setAssignedToErased(String username){
		TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAssignee", Ticket.class);
		query.setParameter("inUsername", username);
		List<Ticket> tickets = query.getResultList();
		if (!tickets.isEmpty()){
			for (Ticket ticket:tickets){
				ticket.setAssignedTo(String.format("%s-erased", username));
				em.merge(ticket);
			}
		}
	}
	
	private void setReportedByToErased(String username){
		TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByReporter", Ticket.class);
		query.setParameter("inUsername", username);
		List<Ticket> tickets = query.getResultList();
		if (!tickets.isEmpty()){
			for (Ticket ticket:tickets){
				ticket.setReportedBy(String.format("%s-erased", username));
				em.merge(ticket);
			}
		}
	}
	
	
	public List<User> getAllUsers(){
	
		try{
			TypedQuery<User> query = em.createNamedQuery("User.findAll", User.class);
			return query.getResultList();
		}
		
		catch(Exception e){
			logger.warning(e.getMessage());
			logger.warning("Unable to fetch all users");
			return null;
		}
	}
	
	
	
	

}
