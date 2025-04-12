package cl.changapp.dto;

public class CloseUsersSearchDto {
	private Long userId;
	private String georeference; 
	private Double radius; 
	private Long sport;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getGeoreference() {
		return georeference;
	}
	public void setGeoreference(String georeference) {
		this.georeference = georeference;
	}
	public Double getRadius() {
		return radius;
	}
	public void setRadius(Double radius) {
		this.radius = radius;
	}
	public Long getSport() {
		return sport;
	}
	public void setSport(Long sport) {
		this.sport = sport;
	}
}
