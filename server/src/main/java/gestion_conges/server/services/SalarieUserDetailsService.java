package gestion_conges.server.services;

import gestion_conges.server.repositories.SalarieRepository;
import gestion_conges.server.security.SalarieUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SalarieUserDetailsService implements UserDetailsService
{
    private SalarieRepository salarieRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var salarie = salarieRepository.findByEmail(username);
        return new SalarieUserDetails(salarie.orElseThrow(() -> new UsernameNotFoundException("Could not find the user.")));
    }
}
