package com.codegans.demo.jbpm.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 21/10/2017 13:29
 */
@Entity
public class Comment extends BaseEntity {
    @CreatedBy
    private String author;
    private String text;
    @CreatedDate
    private LocalDateTime dateTime;

    public Comment() {
    }

    public Comment(Long id, String author, String text) {
        super(id);
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
