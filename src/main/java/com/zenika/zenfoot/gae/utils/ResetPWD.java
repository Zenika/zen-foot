package com.zenika.zenfoot.gae.utils;

/**
 * Created by raphael on 19/08/14.
 */
public class ResetPWD {
    private Long pwdLinkId;

    private String newPWD;

    public ResetPWD(Long pwdLinkId, String newPWD) {
        this.pwdLinkId = pwdLinkId;
        this.newPWD = newPWD;
    }

    public ResetPWD() {
    }

    public Long getPwdLinkId() {
        return pwdLinkId;
    }

    public void setPwdLinkId(Long pwdLinkId) {
        this.pwdLinkId = pwdLinkId;
    }

    public String getNewPWD() {
        return newPWD;
    }

    public void setNewPWD(String newPWD) {
        this.newPWD = newPWD;
    }
}
