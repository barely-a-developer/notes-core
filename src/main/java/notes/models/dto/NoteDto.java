package notes.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class NoteDto {
    private Long id;
    private String message;
    private Date createdDate;
}
