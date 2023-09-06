package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credential {

    private Integer credentialid;
    private String url;
    private String key;
    private String password;
    private String userid;

    public Credential(Integer credentialid, String userid) {
        this.credentialid = credentialid;
        this.userid = userid;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public String getUserid() {
        return userid;
    }
}
