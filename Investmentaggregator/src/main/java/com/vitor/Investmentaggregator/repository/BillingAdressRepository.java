package com.vitor.Investmentaggregator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitor.Investmentaggregator.entities.BillingAddress;

public interface BillingAdressRepository extends JpaRepository<BillingAddress, UUID> {
    
}
