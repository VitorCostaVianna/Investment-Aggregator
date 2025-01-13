package com.vitor.Investmentaggregator.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = BillingAdress.TABLE_NAME)
public class BillingAdress {
    
    private static final String TABLE_NAME = "tb_billing_adress";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "billing_adress_id")
    private UUID billingAdressId;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private int number;

    public BillingAdress() {
    }

    public BillingAdress(UUID billingAdressId, String street, int number) {
        this.setBillingAdressId(billingAdressId);
        this.setStreet(street);
        this.setNumber(number);
    }

    public UUID getBillingAdressId() {
        return billingAdressId;
    }

    public void setBillingAdressId(UUID billingAdressId) {
        this.billingAdressId = billingAdressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
 
}
