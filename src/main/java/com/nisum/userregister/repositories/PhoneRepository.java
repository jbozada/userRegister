package com.nisum.userregister.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nisum.userregister.entities.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, String>{
}
