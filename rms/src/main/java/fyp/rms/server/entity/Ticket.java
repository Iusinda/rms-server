package fyp.rms.server.entity;

import java.util.Date;

public class Ticket {
	private int restaurantID;
	private int customerID;
	private int ticketTypeID;
	private Date getTime;
	private Date callTime;
	private boolean status;
	
	public int getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getTicketTypeID() {
		return ticketTypeID;
	}

	public void setTicketTypeID(int ticketTypeID) {
		this.ticketTypeID = ticketTypeID;
	}

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}

	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
