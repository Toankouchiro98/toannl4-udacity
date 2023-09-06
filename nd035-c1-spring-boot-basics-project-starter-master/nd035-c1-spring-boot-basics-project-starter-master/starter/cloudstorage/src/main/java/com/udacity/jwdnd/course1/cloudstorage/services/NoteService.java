package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    public void add(Note note){
        if(note.getNoteId() == null){
            noteMapper.create(note);
            return;
        }
        noteMapper.update(note);
    }
}
