package com.twealthbook.model;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {

    private Long userId;
    private Role roleId;

    public UserRole(){}

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @OneToOne
    @JoinColumn(name = "role_id")
    public Role getRoleId() {
        return roleId;
    }
    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }
}
