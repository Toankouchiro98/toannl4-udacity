package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    private EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createCredential(@RequestParam Map<String, String> map, Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        if(map.get("url").isEmpty()){
            model.addAttribute("fail","Credential URL must not be empty.");
            return "result";
        }

        if(map.get("username").isEmpty()){
            model.addAttribute("fail","Credential user name must not be empty.");
            return "result";
        }

        if(map.get("password").isEmpty()){
            model.addAttribute("fail","Note Title must not be empty.");
            return "result";
        }

        if("".equals(map.get("credentialId"))){
            var credential = new Credential(null, map.get("url"), map.get("username"), map.get("password"), userId);
            credentialService.add(credential);
        } else {
            credentialService.update(new Credential (Integer.valueOf(map.get("credentialId")) , map.get("url"), map.get("userName"), map.get("password"), userId) );
        }
        model.addAttribute("success", true);
        return "redirect:/result";
    }
    @GetMapping(value = "/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialid, Model model){
        credentialService.delete(credentialid);
        model.addAttribute("delete",true);
        return "redirect:/result";
    }

}
