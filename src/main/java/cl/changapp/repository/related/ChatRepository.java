package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.Chat;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUserOneIdOrUserTwoId(Long userOneId, Long userTwoId);
}
