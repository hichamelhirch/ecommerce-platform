package org.sid.ecommerce.auth;

import lombok.RequiredArgsConstructor;
import org.sid.ecommerce.dtos.SignupRequest;
import org.sid.ecommerce.dtos.USerDTO;
import org.sid.ecommerce.entities.User;
import org.sid.ecommerce.enums.UserRole;
import org.sid.ecommerce.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{



    private final UserRepository userRepository;

    public USerDTO createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        User createdUSer = userRepository.save(user);
        USerDTO uSerDTO = new USerDTO();

        uSerDTO.setId(createdUSer.getUserId());
       return uSerDTO;
     }

    public Boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
     }

    public void createAdminAccount(){
        User adminAccount=userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount==null){
          User user =new User();
          user.setEmail("admin@gmail.com");
          user.setName("admin");
          user.setRole(UserRole.ADMIN);
          user.setPassword(new BCryptPasswordEncoder().encode("admin"));
          userRepository.save(user);

        }
    }
}
