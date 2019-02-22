package fiverr.vos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {

    private String currentPassword;

    private String newPassword;

    private String confirmation;
}
