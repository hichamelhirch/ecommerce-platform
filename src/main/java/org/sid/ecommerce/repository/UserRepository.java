package org.sid.ecommerce.repository;

import org.sid.ecommerce.entities.User;
import org.sid.ecommerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String username);


    User findByRole(UserRole userRole);

}
