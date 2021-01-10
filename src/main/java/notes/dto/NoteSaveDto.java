package notes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import notes.enums.AttachmentType;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class NoteSaveDto {

    private Long id;

    @Size(max = 140)
    private String message;

    private Map<AttachmentType, List<AttachmentSaveDto>> attachmentMap;

}
