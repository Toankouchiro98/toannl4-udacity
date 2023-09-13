package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credential {

    private Integer credentialId;
    private String url;
    private String key;
    private String password;
    private String username;
    private Integer userId;

    public Credential() {
    }

    public Credential(Integer credentialId, String url, String username, String password, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Credential(Integer credentialId, String url, String key, String password, String username, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.key = key;
        this.password = password;
        this.username = username;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
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

    public String getUsername() {
        return username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
