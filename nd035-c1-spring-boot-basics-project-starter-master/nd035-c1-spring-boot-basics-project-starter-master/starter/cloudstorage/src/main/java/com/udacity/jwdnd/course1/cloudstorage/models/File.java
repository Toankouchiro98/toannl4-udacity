package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(Integer fileId, String fileName, String contentType, Long fileSize, Integer userid, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userid;
        this.fileData = fileData;
    }

    public File(Integer fileId, Integer userId) {
        this.fileId = fileId;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
