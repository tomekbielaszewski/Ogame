package org.grizz.service;

import org.springframework.beans.factory.annotation.Autowired;

public class PlayerServiceWithMockedAuthenticationContext extends PlayerService {
    @Autowired
    private Object authenticationContext;

    @Override
    protected Object getAuthenticationContext() {
        return authenticationContext;
    }
}
