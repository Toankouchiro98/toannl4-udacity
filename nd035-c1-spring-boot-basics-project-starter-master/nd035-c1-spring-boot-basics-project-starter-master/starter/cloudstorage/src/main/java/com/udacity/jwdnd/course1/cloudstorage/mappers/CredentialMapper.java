package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT credentialid, url, username, key, password, userid FROM CREDENTIALS WHERE userid = #{userid} ORDER BY credentialid DESC")
    List<Credential> getCredentials(Integer userid);

    @Select("SELECT credentialid, url, username, key, password, userid FROM CREDENTIALS WHERE credential = #{credentialid} AND userid = #{userid}")
    Credential search(Credential credential);

    @Insert("INSERT INTO CREDENTIALs (url, username, key, password, userid) VALUES (#{url}, #{username}, {key}, {password}, {userid})")
    @Options (useGeneratedKeys = true, keyProperty = "credentialid")
    Integer add(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialid}")
    void update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(Integer credentialid);
}
