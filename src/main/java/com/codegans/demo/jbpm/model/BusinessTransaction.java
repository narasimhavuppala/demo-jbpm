package com.codegans.demo.jbpm.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 15:41
 */
@Entity
public class BusinessTransaction implements Serializable {
    @Id
    private UUID id;

    @CreatedDate
    private LocalDateTime dateTime = LocalDateTime.now();

    private Boolean treated = Boolean.FALSE;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isTreated() {
        return treated;
    }

    public void setTreated(boolean treated) {
        this.treated = treated;
    }
}
