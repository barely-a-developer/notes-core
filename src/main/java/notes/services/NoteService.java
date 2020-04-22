package notes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notes.models.Note;
import notes.models.dto.NoteDto;
import notes.repositories.NoteDao;
import notes.services.translators.NoteTranslator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static notes.services.translators.NoteTranslator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteDao noteDao;

    public Optional<NoteDto> getNote(Long id) {
        log.info("Getting Note with ID [{}]", id);
        Optional<Note> noteOptional = noteDao.findById(id);
        if (noteOptional.isPresent()) {
            log.info("Found Note with ID [{}]", id);
        } else {
            log.info("Could not find Note with ID [{}]", id);
        }
        return noteOptional.map(NoteTranslator::translateNoteToNoteDto);
    }

    public List<NoteDto> getNotes(Pageable pageable) {
        log.info("Getting all the Notes");
        List<Note> noteList;
        Page<Note> page = noteDao.findAll(pageable);
        if (page.hasContent()) {
            noteList = page.getContent();
            log.info("Found {} Notes", noteList.size());
            return noteList.stream().map(NoteTranslator::translateNoteToNoteDto).collect(Collectors.toList());
        } else {
            log.info("Notes not found");
            return List.of();
        }
    }

    public NoteDto addNote(NoteDto noteDto) {
        log.info("Adding new note");
        Note note = newNoteFromNoteDto(noteDto);
        // Make sure a new one will be created and not an existing one updated
        note.setId(null);
        Note newNote = noteDao.save(note);
        log.info("Added Note with ID [{}]", newNote.getId());
        return translateNoteToNoteDto(newNote);
    }

    public Optional<NoteDto> updateNote(NoteDto noteDto) {
        Long id = noteDto.getId();
        log.info("Updating Note with ID [{}]", id);
        Optional<Note> noteOptional = noteDao.findById(id);
        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            enrichNoteWithNoteDto(note, noteDto);
            Note updatedNote = noteDao.save(note);
            log.info("Updated Note with ID [{}]", id);
            return Optional.of(translateNoteToNoteDto(updatedNote));
        } else {
            log.info("Could not find to update Note with ID [{}]", id);
            return Optional.empty();
        }
    }

    public boolean deleteNote(Long id) {
        log.info("Deleting Note with ID [{}]", id);
        try {
            noteDao.deleteById(id);
            log.info("Deleted Note with ID [{}]", id);
            return true;
        } catch (EmptyResultDataAccessException ex) {
            log.info("Could not find to delete Note with ID [{}]", id);
            return false;
        }
    }

}
