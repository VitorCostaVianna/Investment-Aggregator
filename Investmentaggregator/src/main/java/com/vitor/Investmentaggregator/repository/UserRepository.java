package com.vitor.Investmentaggregator.repository;

import java.util.UUID;

import com.vitor.Investmentaggregator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
}
