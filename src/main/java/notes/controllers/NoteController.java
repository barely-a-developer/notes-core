package notes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.models.dto.NoteDto;
import notes.services.NoteService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public NoteDto postNote(@RequestBody NoteDto noteDto) {
        return noteService.addNote(noteDto);
    }

    @GetMapping
    public List<NoteDto> getNotes(Pageable pageable) {
        return noteService.getNotes(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNote(@PathVariable("id") Long id) {
        return noteService.getNote(id)
                .map(note -> new ResponseEntity<>(note, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteDto> patchNote(@PathVariable("id") Long id, @RequestBody NoteDto noteDto) {
        noteDto.setId(id);
        return noteService.updateNote(noteDto)
                .map(updatedNote -> new ResponseEntity<>(updatedNote, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable("id") Long id) {
        return  noteService.deleteNote(id) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}