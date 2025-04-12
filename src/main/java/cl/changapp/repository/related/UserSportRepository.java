package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.UserSport;

import java.util.List;

public interface UserSportRepository extends JpaRepository<UserSport, Long> {
    List<UserSport> findByUserId(Long userId);
    
    UserSport findByUserIdAndSportId(Long userId, Long sportId);
}
