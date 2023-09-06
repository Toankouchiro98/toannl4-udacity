package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isHaveFile(Integer userId, String fileName){
        return fileMapper.getFileName(userId, fileName) == null;
    }
    public Integer uploadFile(File file){
        return fileMapper.add(file);
    }

    public File getFile(Integer fileId, Integer userId){
        return fileMapper.getFile(fileId, userId);
    }

    public List<File> getFileList(Integer userId){
        return fileMapper.getFileList(userId);
    }

    public File getFileName(Integer userId, String fileName){
        return fileMapper.getFileName(userId, fileName);
    }

    public void deteleFile(Integer fileId){
        fileMapper.detele(fileId);
    }
}
