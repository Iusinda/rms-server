package rms.server.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.ManyToOne;
import javax.persistence.JoinColumns;
import javax.persistence.JoinColumn;

public class Ticket implements Serializable{
	@ManyToOne(optional=false)
	@JoinColumns({
		@JoinColumn(name="restaurantId"),
		@JoinColumn(name="ticketTypeId")
    })
    private TicketType ticketType;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="customerId", referencedColumnName="id")
    private Customer customer;
	
	private Timestamp getTime;
	
	private Timestamp callTime;
	
	private boolean status;
}
