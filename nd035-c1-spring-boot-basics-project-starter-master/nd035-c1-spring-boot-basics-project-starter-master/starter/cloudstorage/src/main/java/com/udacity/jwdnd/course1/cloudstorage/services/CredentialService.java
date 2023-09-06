package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Integer add(Credential credential){
        return credentialMapper.add(credential);
    }

    public List<Credential> getCredentials(Integer userid){
        return credentialMapper.getCredentials(userid);
    }

    public Credential search(Credential credential){
        return credentialMapper.search(credential);
    }

    public void update(Credential credential){
        credentialMapper.update(credential);
    }

    public void delete(Integer credentialid){
        credentialMapper.delete(credentialid);
    };
}
