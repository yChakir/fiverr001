package fiverr.converter;

import org.springframework.core.convert.converter.Converter;

import fiverr.entity.User;
import fiverr.vos.Profile;

public class UserToProfileConverter implements Converter<User, Profile> {
    @Override
    public Profile convert(User source) {
        return new Profile(
                source.getName(),
                source.getSurname(),
                source.getEmail(),
                source.getPhone(), source.getAvatar() == null ? 0 : source.getAvatar().getId(),
                source.getCreated().toLocalDate()
        );
    }
}
