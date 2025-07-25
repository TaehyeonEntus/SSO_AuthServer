package entus.authServer.repository;

import entus.authServer.domain.AuthProvider;
import entus.authServer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String email);
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String providerId);
}