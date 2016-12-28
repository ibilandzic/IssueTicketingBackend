package hr.codiraona.IssueTicketing.backend.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MESSAGES database table.
 * 
 */
@Entity
@Table(name="MESSAGES", schema="TICKETING")
@NamedQueries({
	@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m"),
	@NamedQuery(name="Message.findByAuthor", query = "SELECT m FROM Message m where m.postedBy=:inUsername")
})

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String message;

	@Column(name="POSTED_BY")
	private String postedBy;

	//bi-directional many-to-one association to Ticket
	@ManyToOne
	@JoinColumn(name="TICKET_ID")
	private Ticket ticket;

	public Message() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPostedBy() {
		return this.postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}

	public Ticket getTicket() {
		return this.ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}