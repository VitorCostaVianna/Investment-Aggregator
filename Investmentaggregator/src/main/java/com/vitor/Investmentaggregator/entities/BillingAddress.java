package com.vitor.Investmentaggregator.entities;

import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = BillingAddress.TABLE_NAME)
public class BillingAddress {
    
    private static final String TABLE_NAME = "tb_billing_adress";

    @Id
    @Column(name = "account_id")
    private UUID id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId // This annotation is used to map the primary key of the target entity as a foreign key to the owning entity.
    @JoinColumn(
        name = "account_id"
    )
    private Account account;

    public BillingAddress() {
    }

    public BillingAddress(String street, int number, Account account) {
        this.setStreet(street);
        this.setNumber(number);
        this.setAccount(account);
    }

    public UUID getBillingAdressId() {
        return id;
    }

    public void setBillingAdressId(UUID id) {
        this.id = id;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
 
}
