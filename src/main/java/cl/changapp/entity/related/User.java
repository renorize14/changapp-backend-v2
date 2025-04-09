package cl.changapp.entity.related;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


	private String firstName;
    private String lastName;
    private String nickname;
    private String birthdate;

    

	@Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String mainCategory;

    private boolean basketball;
    private boolean basketball3x3;
    private boolean football7;
    private boolean football5;

    @Column(name = "profile_picture")
    private String profilePicture;

    private String geoReference;
    
    private Boolean isActive;
    
    public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profile_picture) {
		this.profilePicture = profile_picture;
	}

	public String getGeoReference() {
		return geoReference;
	}

	public void setGeoReference(String geoReference) {
		this.geoReference = geoReference;
	}
	
	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
}