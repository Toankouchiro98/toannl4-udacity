package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private UserService userService;

    private FileService fileService;

    private NoteService noteService;

    private CredentialService credentialService;

    private EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String homePage(Authentication authentication, Model model){
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.getFileList(userId));
        model.addAttribute("notes", noteService.getNoteList(userId));
        model.addAttribute("credentials", credentialService.getCredentials(userId));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @GetMapping("/result")
    public String getResultPage(Model model){
        model.addAttribute("success",true);
        return "result";
    }
}
