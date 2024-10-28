package com.gaboot.backend.master.role.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gaboot.backend.common.entity.BaseEntity;
import com.gaboot.backend.master.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "master_roles")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY) // Establishes the reverse relationship
    @JsonManagedReference
    private List<User> users; // Collection of users with this role
}
