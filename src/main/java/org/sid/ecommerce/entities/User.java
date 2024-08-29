package org.sid.ecommerce.entities;

import jakarta.persistence.*;
import lombok.*;
import org.sid.ecommerce.enums.UserRole;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String name;
    private UserRole role;
  /*  @Lob
  @Lob @Column(columnDefinition="bytea")
    private byte[] img;

   */
}
