package fyp.rms.entity;

public class Customer {
	// id is auto-generated Integer
	private Integer id;
	private String regId;

	public Customer(){
	}

	public Customer(Integer id, String regId){
		this.id = id;
		this.regId = regId;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

}
