package org.openlmis;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openlmis.RightType.SUPERVISION;

public class SupervisionRoleAssignmentTest {

    Right right = Right.ofType(SUPERVISION);
    Program program = new Program();
    SupervisoryNode node = new SupervisoryNode();
    SupervisionRoleAssignment supervisionRoleAssignment = new SupervisionRoleAssignment(Role.group(right), program, node);

    public SupervisionRoleAssignmentTest() throws RightTypeException {
    }

    @Test
    public void shouldHaveRightWhenRightAndProgramAndSupervisoryNodeMatch() throws Exception, RightTypeException {

        //when
        RightQuery rightQuery = new RightQuery(right, program, node);
        boolean hasRight = supervisionRoleAssignment.hasRight(rightQuery);

        //then
        assertTrue(hasRight);
    }

    @Test
    public void shouldNotHaveRightWhenProgramDoesNotMatch() throws Exception, RightTypeException {

        //when
        RightQuery rightQuery = new RightQuery(right, new Program(), node);
        boolean hasRight = supervisionRoleAssignment.hasRight(rightQuery);

        //then
        assertFalse(hasRight);
    }

    @Test
    public void shouldNotHaveRightWhenNodeDoesNotMatch() throws Exception, RightTypeException {

        //when
        RightQuery rightQuery = new RightQuery(right, program, new SupervisoryNode());
        boolean hasRight = supervisionRoleAssignment.hasRight(rightQuery);

        //then
        assertFalse(hasRight);
    }
}
