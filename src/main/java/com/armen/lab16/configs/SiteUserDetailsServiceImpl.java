package com.armen.lab16.configs;

import com.armen.lab16.repositories.SiteUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//TODO: use the @Service here
@Service // Spring autodetects and loads
public class SiteUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  SiteUserRepo siteUserRepo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO: return a SiteUser -> optional cast to UserDetails
    return (UserDetails) siteUserRepo.findByUsername(username);
  }
}
