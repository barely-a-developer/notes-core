package notes.controllers;

import lombok.extern.slf4j.Slf4j;
import notes.models.Note;
import notes.repositories.NoteDao;
import notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteDao noteDao;

    @Autowired
    private NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> postNote(@RequestBody Note note) {
        return new ResponseEntity<>(noteService.addNote(note), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getNotes() {
        List<Note> noteList = noteService.getNotes();
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable(value = "id") Long id) {
        return noteService.getNote(id)
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Note> patchNote(@PathVariable(value = "id") Long id, @RequestBody Note note) {

        if (note.getId() == null) {
            note.setId(id);

        } else if (!note.getId().equals(id)) {
            log.error("ID discrepancy during update");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return noteService.updateNote(note)
                .map(updatedNote -> new ResponseEntity<>(updatedNote, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") Long id) {
        if (noteService.deleteNote(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}