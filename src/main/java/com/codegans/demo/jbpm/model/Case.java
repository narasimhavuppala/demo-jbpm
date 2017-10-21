package com.codegans.demo.jbpm.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 21/10/2017 13:28
 */
@Entity
public class Case extends BaseEntity {
    @OneToMany
    private Set<Alarm> alarms;
    @OneToMany
    private Set<Comment> comments;

    public Case() {
    }

    public Case(Long id, Set<Alarm> alarms) {
        super(id);
        this.alarms = alarms;
    }

    public Set<Alarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(Set<Alarm> alarms) {
        this.alarms = alarms;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
