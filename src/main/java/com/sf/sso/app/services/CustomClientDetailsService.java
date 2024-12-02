package com.sf.sso.app.services;

import com.sf.sso.app.entities.Oauth2Client;
import com.sf.sso.app.repositories.Oauth2ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailsService implements ClientDetailsService {

    private final Oauth2ClientRepository clientRepository;

    public CustomClientDetailsService(Oauth2ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        // Ambil data client dari database
        Optional<Oauth2Client> clientOpt = clientRepository.findByClientId(clientId);
        if (clientOpt.isEmpty()) {
            throw new IllegalArgumentException("Client not found: " + clientId);
        }

        Oauth2Client oauth2Client = clientOpt.get();

        // Buat dan return ClientDetails sesuai dengan data dari database
        return ClientDetails
            .withId(oauth2Client.getClientId())
            .secret(oauth2Client.getClientSecret())
            .authorizedGrantTypes(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUris(oauth2Client.getRedirectUrl())
            .scopes(oauth2Client.getScope().split(","))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .build();
    }
}
