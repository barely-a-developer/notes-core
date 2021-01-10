package notes.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import notes.enums.AttachmentType;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class NoteGetDto {

    private Long id;

    @Size(max = 140)
    private String message;

    private Date createdDate;

    @Builder.Default
    private Map<AttachmentType, List<AttachmentGetDto>> attachmentMap = new HashMap<>();

}
