package fiverr.vos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {

    @NotBlank
    @Size(min = 8, max = 50)
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 50)
    private String newPassword;

    @NotBlank
    @Size(min = 8, max = 50)
    private String confirmation;
}
