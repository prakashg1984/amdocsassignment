package com.amdocs.assignment.bo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Profile {

	@NotNull
	@Size(min=2 , max=30)
	private String userName;
	
	@NotNull
	@Size(min=2 , max=50)
	private String address;
	
	@NotNull
	@Size(min=10 , max=10)
	private String phoneNumber;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Profile [userName=" + userName + ", address=" + address + ", phoneNumber=" + phoneNumber + "]";
	}
}
