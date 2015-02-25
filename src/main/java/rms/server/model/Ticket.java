package rms.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;

import java.sql.Timestamp;

@Entity
@IdClass(TicketId.class)
public class Ticket implements Serializable{
	@Id
	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="restaurantId"),
		@JoinColumn(name="type")
    })
    private TicketType ticketType;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="customerId", referencedColumnName="id")
    private Customer customer;
	
	@Id
	private Timestamp getTime;
	
	private Timestamp callTime;
	
	private boolean status;
}

class TicketId implements Serializable{
	private TicketType ticketType;
	private Timestamp getTime;
}