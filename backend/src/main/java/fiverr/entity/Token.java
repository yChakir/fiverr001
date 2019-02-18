package fiverr.entity;

import fiverr.pojo.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Audited
@AllArgsConstructor
@Table(name = "TOKENS")
@EntityListeners(AuditingEntityListener.class)
public class Token extends BaseEntity implements Serializable {

    public Token() {
        this.token = UUID.randomUUID().toString();
        this.expireDate = LocalDateTime.now().plusDays(1);
    }

    public Token(User user, TokenType type) {
        this();
        this.user = user;
        this.type = type;
    }

    @Id
    @Column(name = "token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_string")
    private String token;

    @NotAudited
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;

    @Column(name = "token_expire_date")
    private LocalDateTime expireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private TokenType type;

    @Column(name = "token_used")
    private boolean used = false;

}
