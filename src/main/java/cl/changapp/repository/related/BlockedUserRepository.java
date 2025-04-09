package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.BlockedUser;

import java.util.List;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    List<BlockedUser> findByBlockerId(Long blockerId);
    boolean existsByBlockerIdAndBlockedId(Long blockerId, Long blockedId);
}
