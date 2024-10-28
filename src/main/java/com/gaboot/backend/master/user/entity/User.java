package com.gaboot.backend.master.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gaboot.backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
//import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "master_users")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String username;

    @Column(length = 100)
    private String email;

    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastname;

    @Column(length = 255)
    @JsonIgnore
    private String password;

    @Column(length = 255)
    private String token;

    @Column()
    private boolean isActive;

    @Column(length = 255)
    private String imagePath;

    @Column(length = 255)
    private String thumbnailPath;

    private UUID roleId;

}
