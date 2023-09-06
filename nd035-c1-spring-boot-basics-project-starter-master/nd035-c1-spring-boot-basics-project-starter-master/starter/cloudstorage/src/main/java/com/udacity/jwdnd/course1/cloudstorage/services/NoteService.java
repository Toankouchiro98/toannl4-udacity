package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNoteList(Integer userId){
        return noteMapper.getNoteList(userId);
    }
    public Integer add(Note note){
        return noteMapper.create(note);
    }

    public Integer edit(Note note){
       return noteMapper.update(note);
    }

    public void delete(Integer noteId){
        noteMapper.delete(noteId);
    }
}
