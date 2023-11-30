package com.application.BitHub.repository;

import java.util.List;
import java.util.Optional;

import com.application.BitHub.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(Long id);

    List<User> findByName(String name);
    List<User> findByNameContaining(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

}
