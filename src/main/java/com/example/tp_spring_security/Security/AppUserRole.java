package com.example.tp_spring_security.Security;

import com.google.common.collect.Sets;

import java.util.Set;

import static com.example.tp_spring_security.Security.AppUserPermission.*;

public enum AppUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE,
            STUDENT_WRITE, STUDENT_READ)),
    ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ,STUDENT_READ));
    private final Set<AppUserPermission> permissions;
   AppUserRole(Set<AppUserPermission> permissions)
    { this.permissions = permissions;
    }
    public Set<AppUserPermission> getPermissions()
    { return permissions;
    }
}
