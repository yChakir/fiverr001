package fiverr.entity;

import fiverr.pojo.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMAILS")
@EntityListeners(AuditingEntityListener.class)
public class Email extends BaseEntity {

    @Id
    @Column(name = "email_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_from")
    private String from;

    @Column(name = "email_to")
    private String to;

    @Column(name = "email_subject")
    private String subject;

    @Column(name = "email_message", length = 3000)
    private String message;

    @Column(name = "email_sent")
    private boolean sent;

    @Column(name = "email_type")
    @Enumerated(EnumType.STRING)
    private EmailType type;
}
