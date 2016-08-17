package org.openlmis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {
    private List<RoleAssignment> roleAssignments = new ArrayList<>();

    public void assignRoles(RoleAssignment... roleAssignments) {
        this.roleAssignments.addAll(Arrays.asList(roleAssignments));
    }

    public List<RoleAssignment> getRoleAssignments() {
        return roleAssignments;
    }

    public boolean hasRight(RightQuery rightQuery) {
        return roleAssignments.stream().anyMatch(roleAssignment -> roleAssignment.hasRight(rightQuery));
    }
}
