package com.zenika.zenfoot.gae.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.joda.time.DateTime;

/**
 * Created by raphael on 18/08/14.
 * <p/>
 * This class contains a user's id. This is useful to generate a link when the user's password is being reinitialized.
 * The id of the object is used in the URI of the page that's sent to the user by mail. The corresponding gambler can be found
 * thanks to this object.
 */
@Entity
public class PWDLink {

    @Id
    private Long id;

    private String userId;

    private DateTime whenCreated;

    /**
     * The max time a PWDLink can stay in the DB before being removed in minutes
     */
    private static int maxTime = 10;

    public PWDLink() {
        this.whenCreated = DateTime.now();
    }

    public PWDLink(String userId) {
        this();
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean mustBeRemoved(){
        DateTime whenRemoved = whenCreated.plusMinutes(maxTime);
        return whenRemoved.isBefore(DateTime.now());
    }
}
