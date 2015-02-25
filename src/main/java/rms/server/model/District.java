package rms.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class District implements Serializable {
	@Id @GeneratedValue
	private long id;
	
	@OneToMany(mappedBy="district", targetEntity=Restaurant.class)
	private List<Restaurant> restaurants; 
}
