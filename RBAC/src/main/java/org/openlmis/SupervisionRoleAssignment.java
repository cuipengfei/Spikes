package org.openlmis;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.openlmis.RightType.SUPERVISION;

public class SupervisionRoleAssignment extends RoleAssignment {
    private Program program;
    private SupervisoryNode supervisoryNode;

    private SupervisionRoleAssignment(Role role) throws RightTypeException {
        super(role);
    }

    public SupervisionRoleAssignment(Role role, Program program) throws RightTypeException {
        super(role);
        this.program = program;
    }

    public SupervisionRoleAssignment(Role role, Program program, SupervisoryNode supervisoryNode) throws RightTypeException {
        super(role);
        this.program = program;
        this.supervisoryNode = supervisoryNode;
    }

    @Override
    protected List<RightType> getAcceptableRightTypes() {
        return singletonList(SUPERVISION);
    }

    @Override
    public boolean hasRight(RightQuery rightQuery) {
        boolean roleMatches = role.contains(rightQuery.getRight());
        boolean programMatches = program.equals(rightQuery.getProgram());

        boolean nodePresentAndMatches = supervisoryNode != null && supervisoryNode.equals(rightQuery.getSupervisoryNode());
        boolean nodeAbsentAndMatches = supervisoryNode == null && rightQuery.getSupervisoryNode() == null;

        boolean nodeMatches = nodePresentAndMatches || nodeAbsentAndMatches;

        return roleMatches && programMatches && nodeMatches;
    }
}
