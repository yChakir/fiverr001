package fiverr.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "IMAGES")
@EntityListeners(AuditingEntityListener.class)
public class Image extends BaseEntity implements Serializable {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name")
    private String name;

    @Lob
    @NotAudited
    @ToString.Exclude
    @Column(name = "image_data")
    private byte[] data;

    @Column(name = "image_share")
    private boolean share = false;

    @NotAudited
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    private User user;

}
