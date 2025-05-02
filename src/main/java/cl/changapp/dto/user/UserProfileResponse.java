package cl.changapp.dto.user;


public class UserProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String nickname;
    private String category;
    private boolean basketball;
    private boolean basketball3x3;
    private boolean football7;
    private boolean football5;
    private boolean padel;
    private String birthdate;
    private String geoReference;
    private String profilePhoto;
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isBasketball() {
		return basketball;
	}
	public void setBasketball(boolean basketball) {
		this.basketball = basketball;
	}
	public boolean isBasketball3x3() {
		return basketball3x3;
	}
	public void setBasketball3x3(boolean basketball3x3) {
		this.basketball3x3 = basketball3x3;
	}
	public boolean isFootball7() {
		return football7;
	}
	public void setFootball7(boolean football7) {
		this.football7 = football7;
	}
	public boolean isFootball5() {
		return football5;
	}
	public void setFootball5(boolean football5) {
		this.football5 = football5;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getGeoReference() {
		return geoReference;
	}
	public void setGeoReference(String geoReference) {
		this.geoReference = geoReference;
	}
	public String getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public boolean isPadel() {
		return padel;
	}
	public void setPadel(boolean padel) {
		this.padel = padel;
	}
	

}
