package rms.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;

@Entity
@IdClass(TicketTypeId.class)
public class TicketType implements Serializable {
	@Id
	@ManyToOne(optional=false)
    @JoinColumn(name="restaurantId", referencedColumnName="id")
    private Restaurant restaurant;
	
	@Id
	@GeneratedValue
	private char type;
	
	private int maxSize;
	
	@OneToMany(mappedBy="ticketType", targetEntity=Ticket.class)
	private List<Ticket> tickets; 
}

class TicketTypeId implements Serializable{
	private Restaurant restaurant;
	private char type;
}