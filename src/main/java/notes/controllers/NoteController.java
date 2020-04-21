package notes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.models.dto.NoteDto;
import notes.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto> postNote(@RequestBody NoteDto noteDto) {
        return new ResponseEntity<>(noteService.addNote(noteDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getNotes() {
        List<NoteDto> noteList = noteService.getNotes();
        return new ResponseEntity<>(noteList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable(value = "id") Long id) {
        return noteService.getNote(id)
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<NoteDto> patchNote(@PathVariable(value = "id") Long id, @RequestBody NoteDto noteDto) {
        noteDto.setId(id);
        return noteService.updateNote(noteDto)
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