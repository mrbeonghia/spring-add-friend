package com.example.demo.security;

import com.example.demo.model.MyUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.Userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private Userrepository repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = repo.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }


    private BCryptPasswordEncoder passencoder = new BCryptPasswordEncoder();

    public void register(User user) {
        String newpass = passencoder.encode(user.getPassword());

        user.setPassword(newpass);
        user.setRole("USER");
        repo.save(user);
    }

    public List<User> listallusers() {
        return repo.findAll();
    }

    public User getuserbyid(Integer id) { return repo.getUserById(id); }

}
