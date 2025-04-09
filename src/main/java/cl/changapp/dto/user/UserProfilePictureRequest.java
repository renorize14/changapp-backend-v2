package cl.changapp.dto.user;

import org.springframework.web.multipart.MultipartFile;

public class UserProfilePictureRequest {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}