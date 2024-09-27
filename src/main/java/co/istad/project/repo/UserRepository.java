package co.istad.project.repo;

import co.istad.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUuid(String uuid);
    Boolean existsByEmail(String email);
    Optional<User> findUsersByEmail(String email);

}
