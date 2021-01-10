package notes.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String message;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL )
    @OrderBy("typeIndex")
    private List<Attachment> attachmentList = new ArrayList<>();

    private String userId;

}
