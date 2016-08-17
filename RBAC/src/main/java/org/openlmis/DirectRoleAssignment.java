package org.openlmis;

import java.util.List;

import static java.util.Arrays.asList;

public class DirectRoleAssignment extends RoleAssignment {
    public DirectRoleAssignment(Role role) throws RightTypeException {
        super(role);
    }

    @Override
    protected List<RightType> getAcceptableRightTypes() {
        return asList(RightType.GENERAL_ADMIN, RightType.REPORTS);
    }
}
