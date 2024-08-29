package org.sid.ecommerce.auth;

import org.sid.ecommerce.dtos.SignupRequest;
import org.sid.ecommerce.dtos.USerDTO;

public interface AuthService {

    USerDTO createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);
}
