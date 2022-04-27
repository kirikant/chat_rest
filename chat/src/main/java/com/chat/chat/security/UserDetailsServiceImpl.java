package com.chat.chat.security;


import com.chat.chat.entity.UserEntity;
import com.chat.chat.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository usersRepo;

    public UserDetailsServiceImpl(UserRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user= usersRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("there is no such user"));
        return SecurityUser.transform(user);
    }
}
