package gestion_conges.server.controllers;

import gestion_conges.server.dto.AuthenticationData;
import gestion_conges.server.entities.Salarie;
import gestion_conges.server.security.SalarieUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AccountController
{
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder; // Only needed to encrypt the password for signing up, when storing it in the database.

    @PostMapping(path = "login")
    @ResponseStatus(HttpStatus.CREATED)
    public Salarie login(@RequestBody AuthenticationData authenticationData)
    {
        var token = new UsernamePasswordAuthenticationToken(authenticationData.getRole(), authenticationData.getRole());
        var authentication = authenticationManager.authenticate(token);
        var salarie = ((SalarieUserDetails)authentication.getPrincipal()).getSalarie();

        return salarie;
    }
}
