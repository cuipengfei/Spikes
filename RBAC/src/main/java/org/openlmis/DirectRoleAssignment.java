package org.openlmis;

import java.util.List;

public class DirectRoleAssignment extends RoleAssignment {
    public DirectRoleAssignment(Role role) throws RightTypeException {
        super(role);
    }

    @Override
    protected List<RightType> getAcceptableRightTypes() {
        return null;
    }

    public static RoleAssignment create(Role role) throws RightTypeException {
        return new DirectRoleAssignment(role);
    }
}
