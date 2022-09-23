package gestion_conges.server.security;

import gestion_conges.server.entities.Salarie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class SalarieUserDetails implements UserDetails
{
    private Salarie salarie;

    public SalarieUserDetails(Salarie salarie)
    {
        this.salarie = salarie;
    }

    public Salarie getSalarie()
    {
        return salarie;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.singleton(new SimpleGrantedAuthority(salarie.getClass().getTypeName()));
    }

    @Override
    public String getPassword()
    {
        return salarie.getPassword();
    }

    @Override
    public String getUsername()
    {
        return salarie.getEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
