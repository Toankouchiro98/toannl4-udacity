package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Integer add(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodeKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodeKey);
        return credentialMapper.add(new Credential(
                null, credential.getUrl(), encodeKey, encryptedPassword, credential.getUsername(), credential.getUserId()
        ));
    }

    public List<Credential> getCredentials(Integer userid){
        return credentialMapper.getCredentials(userid);
    }

    public Credential search(Credential credential){
        return credentialMapper.search(credential);
    }

    public void update(Credential credential){
        String key = credentialMapper.search(credential).getKey();
        String password = credential.getPassword();
        credential.setPassword(encryptionService.encryptValue(password, key));
        credentialMapper.update(credential);
    }

    public void delete(Integer credentialid){
        credentialMapper.delete(credentialid);
    };

}
