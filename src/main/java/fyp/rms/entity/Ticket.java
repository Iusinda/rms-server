package fyp.rms.entity;

import java.sql.Timestamp;

public class Ticket {
	private Integer customerId;
	private Integer restaurantId;
	private Integer type;
	private Integer number;
	private Integer size;
	private Integer position;
	private Integer duration;
	private Timestamp getTime;
	private Timestamp callTime;
	private Boolean validity;

	public Ticket() {
	}

	public Ticket(Integer restaurantId, Integer type) {
		this.restaurantId = restaurantId;
		this.type = type;
	}

	public Ticket(Integer restaurantId, Integer type, Integer number,
			Integer size, Integer customerId) {
		this.restaurantId = restaurantId;
		this.type = type;
		this.number = number;
		this.size = size;
		this.customerId = customerId;
	}

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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
}
