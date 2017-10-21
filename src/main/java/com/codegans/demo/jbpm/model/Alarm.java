package com.codegans.demo.jbpm.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 20/10/2017 16:40
 */
@Entity
public class Alarm extends BaseEntity {
    private Long taskId;
    @OneToOne
    private Anomaly transaction;
    @OneToMany
    private Set<Comment> comments;

    public Alarm() {
    }

    public Alarm(Long id, Long taskId, Anomaly transaction, Set<Comment> comments) {
        super(id);
        this.taskId = taskId;
        this.transaction = transaction;
        this.comments = comments;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Anomaly getTransaction() {
        return transaction;
    }

    public void setTransaction(Anomaly transaction) {
        this.transaction = transaction;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
