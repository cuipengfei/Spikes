package org.openlmis;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UserTest {
    @Test
    public void shouldBeAbleToAssignRoleToUser() throws Exception, RightTypeException {
        //given
        User user = new User();

        //when
        user.assignRoles(new DirectRoleAssignment(Role.group(Right.ofType(RightType.REPORTS))));

        //then
        assertThat(user.getRoleAssignments().size(), is(1));
    }
}
