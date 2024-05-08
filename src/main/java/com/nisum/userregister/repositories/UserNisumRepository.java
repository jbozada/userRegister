package com.nisum.userregister.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nisum.userregister.entities.UserNisum;

@Repository
public interface UserNisumRepository extends JpaRepository<UserNisum, String> {

    Optional<UserNisum> findByEmail (String email);
}