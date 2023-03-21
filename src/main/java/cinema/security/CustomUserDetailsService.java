package cinema.security;

import cinema.model.User;
import cinema.service.UserService;
import java.util.Optional;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(username);
        UserBuilder userBuilder;
        if (userOptional.isPresent()) {
            userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.password(userOptional.get().getPassword());
            userBuilder.roles(userOptional.get().getRoles().stream()
                    .map(x -> x.getRoleName().name())
                    .toArray(String[]::new));
            return userBuilder.build();

        }
        throw new UsernameNotFoundException("User not found.");
    }
}
