package fiverr.converter;

import fiverr.entity.User;
import fiverr.vos.Profile;
import org.springframework.core.convert.converter.Converter;

public class UserToProfileConverter implements Converter<User, Profile> {
    @Override
    public Profile convert(User source) {
        return new Profile(
                source.getName(),
                source.getSurname(),
                source.getEmail(),
                source.getPhone(),
                source.getAvatar()
        );
    }
}
