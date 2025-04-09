package cl.changapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import cl.changapp.dto.user.UserUpdateRequest;
import cl.changapp.entity.related.User;
import cl.changapp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/all")
    public List<User> getAllUsers() {
    	try {
    		return userService.getAllUsers();
		} catch (Exception e) {
			System.out.println(e);
			return null;			
		}
        
    }
    


    @GetMapping("/")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return userService.getUserProfile(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PutMapping("/")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody UserUpdateRequest request) {
        String email = userDetails.getUsername();
        boolean updated = userService.updateUserProfile(email, request);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/profile-picture")
    public ResponseEntity<?> updateProfilePicture(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam("file") MultipartFile file) {

        String email = userDetails.getUsername();
        boolean updated = userService.updateProfilePicture(email, file);
        return updated ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
    
    @PostMapping("/close/request")
    public ResponseEntity<?> requestClosure(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userService.findByEmail(email)
                               .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        userService.requestAccountClosure(user);
        return ResponseEntity.ok("Código de confirmación enviado.");
    }
}