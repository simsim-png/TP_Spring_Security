package com.example.tp_spring_security.Auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<ApplicationUser>
    SelectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream().filter(applicationUser -> username.equals(applicationUser.getUsername())).findFirst();
    }
    private List<ApplicationUser> getApplicationUsers() {
        List<GrantedAuthority> grantedAuthoritiesAdmin= new ArrayList<>();
        List<GrantedAuthority> grantedAuthoritiesUser= new ArrayList<>();
        grantedAuthoritiesUser.add(new SimpleGrantedAuthority("USER"));
        grantedAuthoritiesAdmin.add(new SimpleGrantedAuthority("Admin"));

        return Lists.newArrayList(
                new ApplicationUser(grantedAuthoritiesAdmin,"admin", "pwd",
// ADMIN.getGrantedAuthorities(),
                        true, true, true, true),
                new ApplicationUser(grantedAuthoritiesUser,"yahya", "wd",
// STUDENT.getGrantedAuthorities(),
                        true, true, true, true)
        );
    }
}
