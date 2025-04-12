package cl.changapp.dto;

public class UserSportDto {
	
	int userId;
	int sportId;
	String userEmail;
	String primaryPosition;
	String secondaryPosition;
	String description;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getSportId() {
		return sportId;
	}
	public void setSportId(int sportId) {
		this.sportId = sportId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPrimaryPosition() {
		return primaryPosition;
	}
	public void setPrimaryPosition(String primaryPosition) {
		this.primaryPosition = primaryPosition;
	}
	public String getSecondaryPosition() {
		return secondaryPosition;
	}
	public void setSecondaryPosition(String secondaryPosition) {
		this.secondaryPosition = secondaryPosition;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
