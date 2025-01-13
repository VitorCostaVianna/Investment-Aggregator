package com.vitor.Investmentaggregator.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = Account.TABLE_NAME)
public class Account {
    
    private static final String TABLE_NAME = "tb_accounts";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID accountId;

    @ManyToOne()
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "user_id"
    )
    private User user;

    @OneToOne(mappedBy = "account")
    @PrimaryKeyJoinColumn  // This annotation is used to define the primary key column that is used as a foreign key to join to another table.
    private BillingAdress billingAdress;

    @Column(name = "description")
    private String description;

    public Account() {
    }

    public Account(UUID accountId, String description) {
        this.setAccountId(accountId);
        this.setDescription(description);
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
