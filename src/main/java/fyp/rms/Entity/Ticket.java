package fyp.rms.Entity;

import java.sql.Timestamp;

public class Ticket {
	private Integer customerId;
	private Integer restaurantId;
	private String type;
	private Timestamp getTime;
	private Timestamp callTime;
	private Boolean validity;
	private Integer size;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getGetTime() {
		return getTime;
	}

	public void setGetTime(Timestamp getTime) {
		this.getTime = getTime;
	}

	public Timestamp getCallTime() {
		return callTime;
	}

	public void setCallTime(Timestamp callTime) {
		this.callTime = callTime;
	}

	public Boolean getValidity() {
		return validity;
	}

	public void setValidity(Boolean validity) {
		this.validity = validity;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
