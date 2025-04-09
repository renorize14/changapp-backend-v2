package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.Sport;

import java.util.Optional;

public interface SportRepository extends JpaRepository<Sport, Long> {
    Optional<Sport> findByName(String name);
}
