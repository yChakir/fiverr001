package fiverr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTACT")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contact extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "contact_id")
    private Long id;

    @Column(name = "contact_name", nullable = false)
    private String name;

    @Column(name = "contact_message", nullable = false)
    private String message;

    @Column(name = "contact_email", nullable = false)
    private String email;
}
