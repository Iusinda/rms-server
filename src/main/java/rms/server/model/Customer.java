package rms.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer implements Serializable {
	@Id @GeneratedValue
	private long id;
	
	private String regId;
	
	@OneToMany(mappedBy="customer", targetEntity=Ticket.class)
	private List<Ticket> tickets; 
}
