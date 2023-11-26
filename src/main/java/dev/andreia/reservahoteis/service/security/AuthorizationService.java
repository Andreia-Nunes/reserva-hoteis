package dev.andreia.reservahoteis.service.security;

import dev.andreia.reservahoteis.model.dtos.security.RegisterDto;
import dev.andreia.reservahoteis.model.security.User;
import dev.andreia.reservahoteis.repository.security.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository repository;

    public AuthorizationService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public User register(RegisterDto data){
        if(this.loginAlreadyExists(data.login())){
            throw new IllegalArgumentException("This login already exists.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        return repository.save(newUser);
    }

    private boolean loginAlreadyExists(String login){
        if(repository.findByLogin(login) != null){
            return true;
        } else {
            return false;
        }
    }
}
