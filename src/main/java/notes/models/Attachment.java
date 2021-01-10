package notes.models;

import lombok.Data;
import notes.enums.AttachmentType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long fileId;

    private AttachmentType type;
    private int typeIndex;

}
