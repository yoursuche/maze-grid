/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author uchechukwu
 */
@Entity
@Table(name = "APP_USER", indexes = {
    @Index(name = "CPD_APPUSER_INDEX", columnList = "USERNAME")})
@Getter
@Setter
public class AppUser extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 5877673919423786302L;
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(nullable = true, name = "ACCOUNT_NON_EXPIRED", columnDefinition = "boolean default true")
    private boolean accountNonExpired = true;

    @Column(nullable = true, name = "ACCOUNT_NON_LOCKED", columnDefinition = "boolean default true")
    private boolean accountNonLocked = true;

    @Column(nullable = true, name = "CREDENTIALS_NON_EXPIRED", columnDefinition = "boolean default true")
    private boolean credentialsNonExpired = true;

    @Column(name = "PASSWORD", length = 1024, nullable = true)
    private String password;

    @Column(name = "ENABLED", columnDefinition = "boolean default true", nullable = false)
    private boolean enabled = true;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Maze> userMazes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userRoles != null) {
            userRoles.forEach((userRole) -> {
                authorities.add(new SimpleGrantedAuthority(userRole.getAppRole().getName()));
            });
        }
        return authorities;
    }

}
