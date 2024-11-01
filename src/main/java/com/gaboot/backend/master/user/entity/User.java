package com.gaboot.backend.master.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaboot.backend.common.entity.BaseEntity;
import com.gaboot.backend.config.LazyLoadFilterSerializer;
import com.gaboot.backend.master.role.entity.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "master_users")
@Getter @Setter
@ToString(exclude = "role")
@AllArgsConstructor @NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

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

    @ManyToOne(fetch = FetchType.LAZY) // Establishes the relationship
    @JoinColumn(name = "role_id", referencedColumnName = "id") // Foreign key column
    @JsonSerialize(using = LazyLoadFilterSerializer.class)
    private Role role;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
