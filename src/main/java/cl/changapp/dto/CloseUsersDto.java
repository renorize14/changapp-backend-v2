package cl.changapp.dto;

public class CloseUsersDto {
	
	private Long id;
	private String nickname;
	private String primaryPosition;
	private String secondaryPosition;
	private String georeference;
	private int age;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getGeoreference() {
		return georeference;
	}
	public void setGeoreference(String georeference) {
		this.georeference = georeference;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	

}
