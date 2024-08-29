package org.sid.ecommerce.dtos;

import lombok.Getter;
import lombok.Setter;
import org.sid.ecommerce.enums.UserRole;

@Getter @Setter
public class USerDTO {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
