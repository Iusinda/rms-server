package rms.server.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.mapping.Collection;

public class District implements Serializable {
	@Id @GeneratedValue
	private long id;
	
	@OneToMany(mappedBy="district", targetEntity=Restaurant.class)
	private Collection restaurants; 
}
