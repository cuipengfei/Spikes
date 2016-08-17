package org.openlmis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class Role {
    private List<Right> rights;

    private Role(List<Right> rights) {
        this.rights = rights;
    }

    public static Role group(Right... rights) throws RightTypeException {
        List<Right> rightsList = new ArrayList<>(asList(rights));
        if (checkRightTypesMatch(rightsList)) {
            return new Role(rightsList);
        } else {
            throw new RightTypeException();
        }

    }

    public List<Right> getRights() {
        return rights;
    }

    public RightType getRightType() {
        return rights.get(0).getRightType();
    }

    private static boolean checkRightTypesMatch(List<Right> rightsList) throws RightTypeException {
        if (rightsList.size() > 0) {
            RightType rightType = rightsList.get(0).getRightType();
            return rightsList.stream().allMatch(right -> right.getRightType() == rightType);
        }

        return false;
    }

    public void add(Right... additionalRights) throws RightTypeException {
        List<Right> allRights = concat(rights.stream(), asList(additionalRights).stream()).collect(toList());

        if (checkRightTypesMatch(allRights)) {
            rights.addAll(Arrays.asList(additionalRights));
        } else {
            throw new RightTypeException();
        }
    }
}
