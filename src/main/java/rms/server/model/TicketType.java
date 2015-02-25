package rms.server.model;

import java.io.Serializable;
import javax.persistence.IdClass;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

import org.hibernate.mapping.Collection;

@IdClass(TicketTypeId.class)
public class TicketType implements Serializable {
	@Id
	@ManyToOne(optional=false)
    @JoinColumn(name="restaurantId")
    private Restaurant restaurant;
	
	@Id
	@GeneratedValue
	private char ticketTypeId;
	
	private int maxSize;
	
	@OneToMany(mappedBy="ticketType", targetEntity=Ticket.class)
	private Collection tickets; 
}

class TicketTypeId {
	private Restaurant restaurant;
	private char ticketTypeId;
}