package cl.changapp.entity.related;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
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

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String mainCategory;

    private boolean basketball;
    private boolean basketball3x3;
    private boolean football7;
    private boolean football5;

    @Lob
    private String profilePictureBase64;

    private String geoReference;
    
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

	public String getProfilePictureBase64() {
		return profilePictureBase64;
	}

	public void setProfilePictureBase64(String profilePictureBase64) {
		this.profilePictureBase64 = profilePictureBase64;
	}

	public String getGeoReference() {
		return geoReference;
	}

	public void setGeoReference(String geoReference) {
		this.geoReference = geoReference;
	}
}