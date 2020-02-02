package notes.services;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import notes.models.Note;
import notes.repositories.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NoteService {

    @Autowired
    private NoteDao noteDao;

    public Optional<Note> getNote(@NonNull Long id) {

        log.info("Getting Note with ID [{}]", id);

        Optional<Note> noteOptional = noteDao.findById(id);

        noteOptional.ifPresentOrElse(note -> log.info("Found Note with ID [{}]", id),
                () -> log.info("Could not find Note with ID [{}]", id));

        return noteOptional;
    }

    public List<Note> getNotes() {

        log.info("Getting all the Notes");

        List<Note> noteList = noteDao.findAll();

        log.info("Returning {} Notes", noteList.size());

        return noteList;
    }

    public Note addNote(Note note) {

        log.info("Adding new note");

        note.setId(null);
        Note newNote = noteDao.save(note);

        log.info("Added Note with ID [{}]", newNote.getId());

        return newNote;
    }

    public Optional<Note> updateNote(Note note) {
        Long id = note.getId();

        log.info("Updating Note with ID [{}]", id);

        Optional<Note> noteOptional = noteDao.findById(id);

        if (noteOptional.isPresent()) {
            Note updatedNote = noteDao.save(note);
            log.info("Updated Note with ID [{}]", id);
            // TODO CreatedDate returns null after update, but is OK in DB
            return Optional.of(updatedNote);
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
