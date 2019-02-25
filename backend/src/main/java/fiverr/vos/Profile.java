package fiverr.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
    private String surname;

    @Email
    @NotBlank
    @Size(min = 3, max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 50)
    private String phone;

    @NotBlank
    @Size(min = 3, max = 100)
    private String avatar;

    @NotNull
    private LocalDate createdAt;
}
