package ru.mdkardaev.security.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.mdkardaev.user.entity.User;
import ru.mdkardaev.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with login [%s] not found", username));
        }

        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles()
                                                              .stream()
                                                              .map(
                                                                      e -> new SimpleGrantedAuthority(e.name()))
                                                              .collect(Collectors.toList());

        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getLogin(),
                        user.getPassword(),
                        grantedAuthorities);
        
        userDetails.eraseCredentials();

        return userDetails;
    }

}
