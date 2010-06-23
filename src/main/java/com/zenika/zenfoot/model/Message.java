package com.zenika.zenfoot.model;

import java.io.Serializable;
import java.util.Date;

public class Message extends AbstractModel implements Serializable, Comparable<Message> {

    private Player player;
    private Date datetime;
    private String message;

    public Message() {
        this.datetime = new Date();
    }

    public Message(Player player, String message) {
        assert player != null : "new Message: player must not be null";
        assert message != null : "new Message: message must not be null";

        this.player = player;
        this.message = message;
        this.datetime = new Date();
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Message{" + "player=" + player + "datetime=" + datetime + "message=" + message + '}';
    }

    @Override
    public int compareTo(Message otherMessage) {
        return datetime.compareTo(otherMessage.datetime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (this.player != other.player && (this.player == null || !this.player.equals(other.player))) {
            return false;
        }
        if (this.datetime != other.datetime && (this.datetime == null || !this.datetime.equals(other.datetime))) {
            return false;
        }
        if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.player != null ? this.player.hashCode() : 0);
        hash = 97 * hash + (this.datetime != null ? this.datetime.hashCode() : 0);
        hash = 97 * hash + (this.message != null ? this.message.hashCode() : 0);
        return hash;
    }
}
