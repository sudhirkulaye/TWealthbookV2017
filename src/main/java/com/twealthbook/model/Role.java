package com.twealthbook.model;

import javax.persistence.*;

/**
 * Day0 Table
 * role_id --> role_name
 * 1 --> ADMIN
 * 2 --> END_USER
 * 3 --> RELATIONSHIP_MANAGER
 */
@Entity
@Table(name = "role")
public class Role {

    private int roleId;
    private String roleName;

    public Role(){}

    @Id
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "role_name", nullable = false)
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
