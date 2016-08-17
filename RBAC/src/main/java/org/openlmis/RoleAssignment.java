package org.openlmis;

import java.util.List;

public abstract class RoleAssignment {
    protected Role role;

    public RoleAssignment(Role role) throws RightTypeException {
        List<RightType> acceptableRightTypes = getAcceptableRightTypes();
        boolean roleTypeAcceptable = acceptableRightTypes.stream().anyMatch(rightType -> rightType == role.getRightType());
        if (!roleTypeAcceptable) {
            throw new RightTypeException();
        }

        this.role = role;
    }

    protected abstract List<RightType> getAcceptableRightTypes();

    public abstract boolean hasRight(RightQuery rightQuery);
}
