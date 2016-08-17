package org.openlmis;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {
    RightQuery rightQuery = new RightQuery(Right.ofType(RightType.SUPERVISION));

    RoleAssignment assignment1 = mock(RoleAssignment.class);

    RoleAssignment assignment2 = mock(RoleAssignment.class);

    User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void shouldBeAbleToAssignRoleToUser() throws Exception, RightTypeException {
        //given


        //when
        user.assignRoles(new DirectRoleAssignment(Role.group(Right.ofType(RightType.REPORTS))));

        //then
        assertThat(user.getRoleAssignments().size(), is(1));
    }

    @Test
    public void shouldHaveRightIfAnyRoleAssignmentHasRight() throws Exception {
        //given
        user.assignRoles(assignment1);
        user.assignRoles(assignment2);

        when(assignment1.hasRight(rightQuery)).thenReturn(true);
        when(assignment2.hasRight(rightQuery)).thenReturn(false);

        //when
        boolean hasRight = user.hasRight(rightQuery);

        //then
        assertTrue(hasRight);
    }

    @Test
    public void shouldNotHaveRightIfNoRoleAssignmentHasRight() throws Exception {
        //given
        user.assignRoles(assignment1);
        user.assignRoles(assignment2);

        when(assignment1.hasRight(rightQuery)).thenReturn(false);
        when(assignment2.hasRight(rightQuery)).thenReturn(false);

        //when
        boolean hasRight = user.hasRight(rightQuery);

        //then
        assertFalse(hasRight);
    }
}
