package rms.server.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Restaurant implements Serializable {
	@Id @GeneratedValue
	private long id;
	
	private String password;
	
	private String name;
	
	@ManyToOne
    @JoinColumn(name="districtId", referencedColumnName="id")
    private District district;
	
	private String address;
	
	private String phoneNo;
	
	private String openingHours;
	
	private String description;
	
	@OneToMany(mappedBy="restaurant", targetEntity=TicketType.class)
	private List<TicketType> ticketTypes;
	
	private boolean status;
}
