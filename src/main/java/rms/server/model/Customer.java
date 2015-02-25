package rms.server.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Customer implements Serializable {
	@Id @GeneratedValue
	private long id;
	
	private String regId;
}
