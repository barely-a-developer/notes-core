package notes.services.translators;

import lombok.experimental.UtilityClass;
import notes.models.Note;
import notes.models.dto.NoteDto;

import java.util.stream.Collectors;

@UtilityClass
public class NoteTranslator {

    public Note newNoteFromNoteDto(NoteDto noteDto) {
        Note note = new Note();
        enrichNoteWithNoteDto(note, noteDto);
        return note;
    }

    public void enrichNoteWithNoteDto(Note note, NoteDto noteDto) {
        note.setId(noteDto.getId());
        note.setMessage(noteDto.getMessage());
        note.setCreatedDate(note.getCreatedDate());
        note.setAttachments(noteDto.getAttachments().stream()
                                   .map(AttachmentTranslator::newAttachmentFromAttachmentDto)
                                   .collect(Collectors.toList()));
    }

    public NoteDto translateNoteToNoteDto(Note note) {
        return NoteDto.builder()
                      .id(note.getId())
                      .message(note.getMessage())
                      .createdDate(note.getCreatedDate())
                      .attachments(note.getAttachments().stream()
                                       .map(AttachmentTranslator::translateAttachmentToAttachmentDto)
                                       .collect(Collectors.toList()))
                      .build();
    }

}
