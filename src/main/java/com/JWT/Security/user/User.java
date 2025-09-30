package com.JWT.Security.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data  // this one for getters and setters
@Builder // build an object in an easy way
@NoArgsConstructor
@AllArgsConstructor
@Entity
/* an entity is a Java class mapped to a database table.
You mark it with @Entity, give it a primary key with @Id, and each field becomes a column.
 */

// USER is a reserved keyword in PostgreSQL.
// so that's why we use the annotaion Table otherwise it would take the class name
@Table(name = "_user")


public class User implements UserDetails // this is for the security
{
    @Id // this means this is the PK
    @GeneratedValue // this to make it auto generated
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING) // this tell spring that this is an enum as a string
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
