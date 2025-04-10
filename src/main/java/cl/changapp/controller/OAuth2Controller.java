package cl.changapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.changapp.config.JwtService;
import cl.changapp.config.UserDetailsImpl;
import cl.changapp.entity.related.User;
import cl.changapp.repository.related.UserRepository;

@RestController
@RequestMapping("/api/oauth2")
public class OAuth2Controller {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public OAuth2Controller(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @GetMapping("/success")
    public ResponseEntity<?> oauth2Success(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstName(name);
            newUser.setPassword("");
            newUser.setAuthProvider("GOOGLE");
            return userRepository.save(newUser);
        });
        


        // Generar token JWT
        String token = jwtService.generateToken(new UserDetailsImpl(user));

        // Enviar token y datos del usuario al frontend
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of("id", user.getId(), "name", user.getFirstName(), "email", user.getEmail()));
        
        System.out.println(response);

        return ResponseEntity.ok(response);
    }
}
