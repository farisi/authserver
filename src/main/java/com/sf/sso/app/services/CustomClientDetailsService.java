package com.sf.sso.app.services;


import org.springframework.stereotype.Service;

import com.sf.sso.app.repositories.Oauth2ClientRepository;

@Service
public class CustomClientDetailsService  {

    private final Oauth2ClientRepository clientRepository;

    public CustomClientDetailsService(Oauth2ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

   
}
