package cl.changapp.controller;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.changapp.dto.UserSportDto;
import cl.changapp.entity.related.UserSport;
import cl.changapp.repository.related.SportRepository;
import cl.changapp.repository.related.UserSportRepository;
import cl.changapp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/sport-users")
@SecurityRequirement(name = "bearerAuth")
public class SportsUserController {
	
	private final UserService userService;
    private final SportRepository sportRep;
    private final UserSportRepository usRep;

    public SportsUserController(UserService userService, UserSportRepository usRep,SportRepository sportRep) {
        this.userService = userService;
        this.usRep = usRep;
        this.sportRep = sportRep;
    }
    
    @PutMapping("/")
    public String editUserSportsPreferences(@RequestBody UserSportDto request) throws JsonProcessingException {
    	System.out.println(new ObjectMapper().writeValueAsString(request));
    	try {
    		UserSport usrSport = usRep.findByUserIdAndSportId(Long.parseLong(request.getUserId()+""), Long.parseLong(request.getSportId()+""));
    		
    		if ( usrSport == null ) {
    			usrSport = new UserSport();
    		}
        	
        	usrSport.setPrimaryPosition(request.getPrimaryPosition());
        	usrSport.setSecondaryPosition(request.getSecondaryPosition());
        	usrSport.setDescription(request.getDescription());
        	
        	usRep.save(usrSport);
        	
        	return "success";
		} catch (Exception e) {
			System.out.println(e);
			return "error";
		}
    	
    }

}
