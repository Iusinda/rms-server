package rms.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.mapping.Collection;

@Entity
public class Restaurant implements Serializable {
	@Id @GeneratedValue
	private long id;
	
	private String password;
	
	private String name;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="districtId", referencedColumnName="id")
    private District district;
	
	private String address;
	
	private String phoneNo;
	
	private String openingHours;
	
	private String description;
	
	@OneToMany(mappedBy="restaurant", targetEntity=TicketType.class)
	private Collection ticketTypes;
	
	private boolean status;
}
