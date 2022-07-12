/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mvpfactory.maze.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author uchechukwu
 */
@Entity
@Table(name = "USER_ROLE")
@Setter
@Getter

public class UserRole extends BaseEntity {

    private static final long serialVersionUID = -2296422162475969962L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ROLE_FK")
    private AppRole appRole;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_FK")
    private AppUser appUser;
}
