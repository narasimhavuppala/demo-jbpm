package com.codegans.demo.jbpm.model;

import javax.persistence.Entity;
import java.net.URL;

/**
 * JavaDoc here
 *
 * @author id967092
 * @since 21/10/2017 13:34
 */
@Entity
public class Attachment extends BaseEntity {
    private URL url;

    public Attachment() {
    }

    public Attachment(Long id, URL url) {
        super(id);

        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
