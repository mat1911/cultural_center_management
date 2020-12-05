package com.app.cultural_center_management.security;

import com.app.cultural_center_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Qualifier("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(userFromDb -> new User(
                        userFromDb.getUsername(),
                        userFromDb.getPassword(),
                        userFromDb.getIsEnabled(), true, true, true,
                        userFromDb
                                .getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority(role.toString()))
                                .collect(Collectors.toSet())
                )).orElseThrow(() -> new SecurityException("cannot find user with username " + username));
    }
}
