package fyp.rms.Entity;

public class Restaurant {
	private Integer id;
	private Integer districtId;
	private String name;
	private String address;
	private String phoneNo;
	private String openingHours;
	private String description;
	private Boolean availability;

	public Restaurant() {
	}

	public Restaurant(Integer id, Integer districtId, String name,
			String address, String phoneNo, String openingHours,
			String description) {
		this.id = id;
		this.districtId = districtId;
		this.name = name;
		this.address = address;
		this.phoneNo = phoneNo;
		this.openingHours = openingHours;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
}
