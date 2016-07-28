package org.grizz.service;

import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceWithMockedAuthenticationContext extends UserService {
    @Autowired
    private Object authenticationContext;

    @Override
    protected Object getAuthenticationContext() {
        return authenticationContext;
    }
}
