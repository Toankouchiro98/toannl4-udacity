package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createNote(@RequestParam Map<String, String> map, Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();

        if(map.get("noteTitle").isEmpty()){
            model.addAttribute("fail","Note Title must not be empty.");
        }

        if(map.get("noteDescription").isEmpty()){
            model.addAttribute("fail","Note Description must not be empty.");
        }

        if("".equals(map.get("noteId"))){
            var note = new Note(null, map.get("noteTitle"), map.get("noteDescription"), userId);
            noteService.add(note);
        } else {
            noteService.edit(new Note (Integer.valueOf(map.get("noteId")) , map.get("noteTitle"), map.get("noteDescription"), userId) );
        }
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Model model){
        noteService.delete(noteId);
        model.addAttribute("delete",true);
        return "result";
    }
}
