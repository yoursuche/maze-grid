/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author uchechukwu
 */
@Entity
@Table(name = "APP_ROLE")
@Setter
@Getter

public class AppRole extends BaseEntity {

    private static final long serialVersionUID = 4831807884060981485L;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appRole")
    private Set<UserRole> userRoles = new HashSet<>();

}
