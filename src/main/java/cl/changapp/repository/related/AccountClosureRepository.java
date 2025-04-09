package cl.changapp.repository.related;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.changapp.entity.related.AccountClosure;

import java.util.Optional;

public interface AccountClosureRepository extends JpaRepository<AccountClosure, Long> {
    Optional<AccountClosure> findByUserId(Long userId);
    Optional<AccountClosure> findByConfirmationCode(String confirmationCode);
    void deleteByUserId(Long userId);
}