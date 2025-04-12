package cl.changapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.changapp.dto.CloseUsersDto;
import cl.changapp.dto.CloseUsersSearchDto;
import cl.changapp.dto.GeoreferenceEditDto;
import cl.changapp.dto.UserSportDto;
import cl.changapp.dto.user.UserUpdateRequest;
import cl.changapp.entity.related.Sport;
import cl.changapp.entity.related.User;
import cl.changapp.entity.related.UserSport;
import cl.changapp.repository.related.SportRepository;
import cl.changapp.repository.related.UserRepository;
import cl.changapp.repository.related.UserSportRepository;
import cl.changapp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;
    private final SportRepository sportRep;
    private final UserSportRepository usRep;
    private final UserRepository uRep;

    public UserController(UserService userService, UserSportRepository usRep,SportRepository sportRep, UserRepository uRep) {
        this.userService = userService;
        this.usRep = usRep;
        this.sportRep = sportRep;
        this.uRep = uRep;
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
    
    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@PathParam("email") String email) {
        return userService.getUserProfile(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/sport")
    public UserSportDto getUserSport(@PathParam("email") String email, @PathParam("sportId") Long sportId){
    	Optional<User> user = userService.findByEmail(email);
    	Optional<Sport> sport = sportRep.findById(sportId);
    	
    	UserSport userSport = usRep.findByUserIdAndSportId(user.get().getId(),sportId);
    	
    	if ( userSport == null ) {
    		
    		userSport = new UserSport();
    		userSport.setDescription("");
    		userSport.setPrimaryPosition("");
    		userSport.setSecondaryPosition("");
    		userSport.setUser(user.get());
    		userSport.setSport(sport.get());
    		
    		usRep.save(userSport);
    	}
    	
    	UserSportDto usDto = new UserSportDto();
    	
    	usDto.setDescription(userSport.getDescription());
    	usDto.setPrimaryPosition(userSport.getPrimaryPosition());
    	usDto.setSecondaryPosition(userSport.getSecondaryPosition());
    	usDto.setSportId(userSport.getSport().getId().intValue());
    	usDto.setUserId(userSport.getUser().getId().intValue());
    	usDto.setUserEmail(email);
    	
    	return usDto;
    	
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
    
    @PutMapping("/georeference")
    public ResponseEntity<?> updateGeoreference(@RequestBody GeoreferenceEditDto request) throws JsonProcessingException {
    	try {
    		Optional<User> usr = userService.findByEmail(request.getEmail());
            
            User user = usr.get();
            
            user.setGeoReference(request.getGeoreference());
            
            uRep.save(user);
    		return ResponseEntity.ok("Código de confirmación enviado.");
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.internalServerError().body("Error");
		}  
    }
    
    @PutMapping("/close-users")
    public List<CloseUsersDto> getCloseUsers(@RequestBody CloseUsersSearchDto request ){
    	return userService.getCloseUsers(request.getUserId(), request.getGeoreference(), request.getRadius(), request.getSport());
    }
}