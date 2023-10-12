package org.online.lms.security.domain.role;


public enum MemberRole {
    ADMIN("ADMIN");

    private final String roleName;

    MemberRole(String roleName){
        this.roleName = roleName;
    }

    public String getRoleName(){
        return roleName;
    }
}
