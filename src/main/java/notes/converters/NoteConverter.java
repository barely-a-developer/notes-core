package notes.converters;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import notes.dto.AttachmentSaveDto;
import notes.dto.NoteGetDto;
import notes.dto.NoteSaveDto;
import notes.enums.AttachmentType;
import notes.models.Note;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NoteConverter {

    private final AttachmentConverter attachmentConverter;

    public Note newEntityFromDto(@NonNull NoteSaveDto noteSaveDto, @NonNull String userId) {
        Note note = new Note();
        enrichEntityWithDto(note, noteSaveDto);
        note.setUserId(userId);
        return note;
    }

    public void enrichEntityWithDto(@NonNull Note note, @NonNull NoteSaveDto noteSaveDto) {
        note.setMessage(noteSaveDto.getMessage());
        Map<AttachmentType, List<AttachmentSaveDto>> attachmentMap = noteSaveDto.getAttachmentMap();
        if (MapUtils.isNotEmpty(attachmentMap)) {
            note.setAttachmentList(attachmentConverter.convertDtoToEntity(attachmentMap));
        }
    }

    public NoteGetDto convertEntityToDto(@NonNull Note note) {
        return NoteGetDto.builder()
                         .id(note.getId())
                         .message(note.getMessage())
                         .createdDate(note.getCreatedDate())
                         .attachmentMap(attachmentConverter.convertEntityToDto(note.getAttachmentList()))
                         .build();
    }

    public List<NoteGetDto> convertEntityToDto(@NonNull List<Note> noteList) {
        return noteList.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

}