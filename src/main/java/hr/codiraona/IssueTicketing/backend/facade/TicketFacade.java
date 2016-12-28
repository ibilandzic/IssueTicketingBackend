package hr.codiraona.IssueTicketing.backend.facade;

import javax.ejb.Local;

import hr.codiraona.IssueTicketing.backend.model.Message;
import hr.codiraona.IssueTicketing.backend.model.Ticket;

@Local
public interface TicketFacade {
	
	void createTicket(Ticket ticket);
	
	void assignTicket(Ticket ticket, String username);
	
	void getAllTickets();
	
	void updateTicket(Ticket ticket);
	
	void addMessage(Message message);
	

}
