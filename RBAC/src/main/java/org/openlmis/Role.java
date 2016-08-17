package org.openlmis;

import java.util.List;

import static java.util.Arrays.asList;

public class Role {
    private List<Right> rights;

    private Role(List<Right> rights) {
        this.rights = rights;
    }

    public static Role group(Right... rights) throws RightTypeException {
        List<Right> rightsList = asList(rights);
        if (checkRightTypesMatch(rightsList)) {
            return new Role(rightsList);
        } else {
            throw new RightTypeException();
        }

    }

    public List<Right> getRights() {
        return rights;
    }

    private static boolean checkRightTypesMatch(List<Right> rightsList) throws RightTypeException {
        if (rightsList.size() > 0) {
            RightType rightType = rightsList.get(0).getRightType();
            return rightsList.stream().allMatch(right -> right.getRightType() == rightType);
        }

        return true;
    }
}
