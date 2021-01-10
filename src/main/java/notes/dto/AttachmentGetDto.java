package notes.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AttachmentGetDto {
    private Long id;
    private Long fileId;
    private FileGetDto fileGetDto;

    @JsonIgnore
    private int typeIndex;
}
