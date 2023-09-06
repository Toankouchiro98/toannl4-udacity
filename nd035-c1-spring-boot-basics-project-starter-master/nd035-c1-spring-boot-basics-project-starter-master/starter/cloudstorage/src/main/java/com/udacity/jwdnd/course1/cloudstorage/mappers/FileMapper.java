package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT fileId, filename, contenttype, filesize, userid, filedata FROM FILES WHERE userid = #{userId} ORDER BY fileId")
    List<File> getFileList(Integer userId);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, filedata FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    File getFile(Integer fileId, Integer userId);

    @Select("SELECT fileId, filename, contenttype, filesize, userid, filedata FROM FILES WHERE userid = #{userId} AND filename = #{fileName}")
    File getFileName(Integer userId, String fileName);

    @Insert("INSERT INTO FILES(filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer add(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void detele(Integer fileId);
}
