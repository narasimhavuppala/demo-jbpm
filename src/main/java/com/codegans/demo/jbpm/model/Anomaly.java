package com.codegans.demo.jbpm.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 15:41
 */
@Entity
public class Anomaly extends BaseEntity {
    private Long taskId;
    @CreatedDate
    private LocalDateTime dateTime;
    @OneToMany
    private Set<Comment> comments;
    private boolean treated;

    public Anomaly() {
    }

    public Anomaly(LocalDateTime dateTime, boolean treated) {
        this(null, dateTime, treated);
    }

    public Anomaly(Long id, LocalDateTime dateTime, boolean treated) {
        super(id)
        ;
        this.dateTime = dateTime;
        this.treated = treated;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public boolean isTreated() {
        return treated;
    }

    public void setTreated(boolean treated) {
        this.treated = treated;
    }
}
