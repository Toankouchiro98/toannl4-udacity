package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT credentialid, url, username, key, password, userid FROM CREDENTIALS WHERE userid = #{userId} ORDER BY credentialid DESC")
    List<Credential> getCredentials(Integer userId);

    @Select("SELECT credentialid, url, username, key, password, userid FROM CREDENTIALS WHERE credential = #{credentialId} AND userid = #{userId}")
    Credential search(Credential credential);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options (useGeneratedKeys = true, keyProperty = "credentialId")
    Integer add(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId}")
    void update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void delete(Integer credentialId);
}
