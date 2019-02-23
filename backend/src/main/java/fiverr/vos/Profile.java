package fiverr.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    private String name;

    private String surname;

    private String email;

    private String phone;

    private String avatar;

    private LocalDate createdAt;
}