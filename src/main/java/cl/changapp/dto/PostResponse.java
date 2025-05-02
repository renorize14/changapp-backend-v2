package cl.changapp.dto;

import java.time.Instant;

public class PostResponse {

	private String _id;
    private Integer user_id;
    private String nickname;
    private String sport;
    private String topic;
    private String body;
    private String profilePicture;
    private Instant timestamp;
    private Boolean activo;
    private String georeference;
    private long likesCount;
    private Boolean likedByUser;

    // Getters y Setters

    
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String image_url) {
        this.profilePicture = image_url;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getGeoreference() {
        return georeference;
    }

    public void setGeoreference(String georeference) {
        this.georeference = georeference;
    }

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public long getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(long l) {
		this.likesCount = l;
	}

	public Boolean getLikedByUser() {
		return likedByUser;
	}

	public void setLikedByUser(Boolean likedByUser) {
		this.likedByUser = likedByUser;
	}
}
