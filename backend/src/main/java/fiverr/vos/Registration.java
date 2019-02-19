package fiverr.vos;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class Registration {

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 30)
    private String surname;

    @Email
    private String email;

    @Size(min = 2, max = 30)
    private String password;
}
