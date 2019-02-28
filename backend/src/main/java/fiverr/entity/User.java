package fiverr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements Serializable, UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "user_name", nullable = false)
    private String name;

    @NotBlank
    @Size(min = 2, max = 30)
    @Column(name = "user_surname", nullable = false)
    private String surname;

    @Email
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "user_email", unique = true, nullable = false)
    private String email;

    @Size(min = 6)
    @NotBlank
    @Column(name = "user_password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Size(max = 30)
    @Column(name = "user_avatar")
    private String avatar = "default";

    @Size(max = 20)
    @Column(name = "user_phone")
    private String phone;

    private boolean active = false;

    @NotAudited
    @OneToMany(targetEntity = Token.class, mappedBy = "user")
    private List<Token> tokens = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", active=" + active +
                ", tokens=" + tokens +
                '}';
    }
}
