package com.codegans.demo.jbpm.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 16:40
 */
@Entity
public class Alarm implements Serializable {
    @Id
    private UUID id;

    @ManyToOne
    private BusinessTransaction transaction;

    private String description;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BusinessTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(BusinessTransaction transaction) {
        this.transaction = transaction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
