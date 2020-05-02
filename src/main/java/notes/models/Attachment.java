package notes.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    String originalUrl;
    String storageId;

}
