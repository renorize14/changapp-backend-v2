package cl.changapp.service;


import cl.changapp.dto.CloseUsersDto;
import cl.changapp.dto.user.UserProfileResponse;
import cl.changapp.dto.user.UserUpdateRequest;
import cl.changapp.entity.related.AccountClosure;
import cl.changapp.entity.related.User;
import cl.changapp.entity.related.UserSport;
import cl.changapp.repository.related.AccountClosureRepository;
import cl.changapp.repository.related.UserRepository;
import cl.changapp.repository.related.UserSportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {

    private final UserRepository userRepository;
    private AccountClosureRepository closureRepository;
    private UserSportRepository uSportRep;

    @Autowired
    public UserService(UserRepository userRepository,AccountClosureRepository closureRepository, UserSportRepository uSportRep) {
        this.userRepository = userRepository;
        this.closureRepository = closureRepository;
        this.uSportRep = uSportRep;
    }

    public Optional<UserProfileResponse> getUserProfile(String email) {
        return userRepository.findByEmail(email).map(this::mapToUserProfileResponse);
    }
    
    public Optional<User> findByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers(){
    	return userRepository.findAll();
    }

    public boolean updateUserProfile(String email, UserUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setNickname(request.getNickname());
        user.setMainCategory(request.getCategory());
        user.setBasketball(request.isBasketball());
        user.setBasketball3x3(request.isBasketball3x3());
        user.setFootball7(request.isFootball7());
        user.setFootball5(request.isFootball5());
        user.setBirthdate(request.getBirthdate());
        user.setGeoReference(request.getGeoReference());
        user.setProfilePicture(request.getProfilePhoto());

        userRepository.save(user);
        return true;
    }

    private UserProfileResponse mapToUserProfileResponse(User user) {
        UserProfileResponse response = new UserProfileResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setNickname(user.getNickname());
        response.setCategory(user.getMainCategory());
        response.setBasketball(user.isBasketball());
        response.setBasketball3x3(user.isBasketball3x3());
        response.setFootball7(user.isFootball7());
        response.setFootball5(user.isFootball5());
        response.setBirthdate(user.getBirthdate());
        response.setGeoReference(user.getGeoReference());
        response.setProfilePhoto(user.getProfilePicture());
        return response;
    }
    
    public boolean updateProfilePicture(String email, MultipartFile file) {
        return userRepository.findByEmail(email).map(user -> {
            try {
                // Aquí podrías guardar el archivo en el sistema de archivos, AWS S3, etc.
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path path = Paths.get("uploads/" + fileName);
                Files.copy(file.getInputStream(), path);

                user.setProfilePicture(fileName);
                userRepository.save(user);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }).orElse(false);
    }
    
    

    public void requestAccountClosure(User user) {
        String confirmationCode = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // Ej: 10 min para confirmar

        AccountClosure closure = new AccountClosure();
        closure.setUser(user);
        closure.setConfirmationCode(confirmationCode);
        closure.setExpirationTime(expirationTime);
        closure.setConfirmed(false);

        closureRepository.save(closure);

        // Lógica para enviar el código por correo (placeholder)
        System.out.println("Código de cierre de cuenta: " + confirmationCode);
    }
    
    public boolean confirmAccountClosure(String email, String code) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        Optional<AccountClosure> closureOpt = closureRepository.findByUserId(user.getId());

        if (closureOpt.isEmpty()) return false;

        AccountClosure closure = closureOpt.get();

        if (closure.isConfirmed()) return false;

        if (!closure.getConfirmationCode().equals(code)) return false;

        if (closure.getExpirationTime().isBefore(LocalDateTime.now())) return false;

        // Marcar como confirmada y desactivar usuario
        closure.setConfirmed(true);
        user.setIsActive(false);

        closureRepository.save(closure);
        userRepository.save(user);

        return true;
    }
    
    
    public List<CloseUsersDto> getCloseUsers(Long userId, String georeference, Double radius, Long sport){
    	
    	List<User> users = userRepository.findAll();
    	List<CloseUsersDto> closeUsers = new ArrayList<>();
    	Double lat1 = Double.parseDouble(georeference.split(",")[0].toString());
    	Double lon1 = Double.parseDouble(georeference.split(",")[1].toString());
    
    	for ( int i = 0 ; i < users.size() ; i ++ ) {
    		if ( users.get(i).getId() != userId ) {
    			System.out.println(users.get(i).getId() + ", " + userId);
    			
    			Double lat2 = Double.parseDouble(users.get(i).getGeoReference().split(",")[0].toString());
	        	Double lon2 = Double.parseDouble(users.get(i).getGeoReference().split(",")[1].toString());
	    		
	    		if ( isWithinRange(lat1, lon1, lat2, lon2, radius) ) {
	    			CloseUsersDto closeUser = new CloseUsersDto();
	    			UserSport userSport = uSportRep.findByUserIdAndSportId(users.get(i).getId(), sport);
	    			
	    			if ( userSport != null ) {
	    				closeUser.setGeoreference(users.get(i).getGeoReference());
		    			closeUser.setNickname(users.get(i).getNickname());
		    			closeUser.setId(users.get(i).getId());
		    			closeUser.setPrimaryPosition(userSport.getPrimaryPosition());
		    			closeUser.setSecondaryPosition(userSport.getSecondaryPosition());
		    			closeUser.setAge(calcularEdad(users.get(i).getBirthdate()));
		    			
		    			closeUsers.add(closeUser);   
	    			}
	    			 			
	    		}
    		}
	    		
    		
    	}
    	
    	return closeUsers;
    	
    }
    
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    	final double EARTH_RADIUS = 6371.0;	
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        System.out.println(EARTH_RADIUS * c);
        return EARTH_RADIUS * c;
    }
    
    public boolean isWithinRange(double lat1, double lon1, double lat2, double lon2, double radius) {
        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        return distance <= radius;
    }
    
    public static int calcularEdad(String fechaNacimientoStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter);
        LocalDate fechaActual = LocalDate.now();

        return Period.between(fechaNacimiento, fechaActual).getYears();
    }
}