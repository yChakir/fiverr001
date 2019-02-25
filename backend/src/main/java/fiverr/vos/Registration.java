package fiverr.vos;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class Registration {

    @Size(min = 2, max = 50)
    private String name;

    @Size(min = 2, max = 50)
    private String surname;

    @Email
    @NotBlank
    @Size(min = 3, max = 100)
    private String email;

    @Size(min = 8, max = 50)
    private String password;
}
