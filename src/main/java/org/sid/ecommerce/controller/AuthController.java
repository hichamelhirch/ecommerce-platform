package org.sid.ecommerce.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.sid.ecommerce.auth.AuthServiceImpl;
import org.sid.ecommerce.dtos.AuthenticationRequest;
import org.sid.ecommerce.dtos.SignupRequest;
import org.sid.ecommerce.dtos.USerDTO;
import org.sid.ecommerce.entities.User;
import org.sid.ecommerce.repository.UserRepository;
import org.sid.ecommerce.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthServiceImpl authService;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING="Authorization";


    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                       HttpServletResponse response) throws JSONException, IOException {

     try {
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));

     }catch (BadCredentialsException badCredentialsException){
         throw new RuntimeException("Incorrect username or password");
     }
     final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt=jwtUtil.generateToken(userDetails.getUsername());
        if (optionalUser.isPresent()){
            response.getWriter()
                    .write(new JSONObject()
                            .put("userId",optionalUser.get().getUserId())
                            .put("role",optionalUser.get().getRole())
                            .toString());
            response.setHeader("Access-Control-Expose-Headers","Authorizationn");
            response.addHeader("Access-Control-Allow-Headers","Authorization,X-PINGOTHER, Origin,"+
                    "X-Requested-With,Content-Type,Accept,X-Customheader");
            response.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt);
        }
 }

 @PostMapping("/sign-up")
    public ResponseEntity<?> signupUSer(@RequestBody SignupRequest signupRequest){
        if (authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exists", HttpStatus.NOT_ACCEPTABLE);
        }
     USerDTO uSerDTO=authService.createUser(signupRequest);
        return new ResponseEntity<>(uSerDTO,HttpStatus.OK);
 }

}
