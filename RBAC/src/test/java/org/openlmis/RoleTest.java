package org.openlmis;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RoleTest {
    @Test
    public void shouldGroupRightsOfSameType() throws Exception, RightTypeException {
        //given
        Right right1 = Right.ofType(RightType.ORDER_FULFILLMENT);
        Right right2 = Right.ofType(RightType.ORDER_FULFILLMENT);

        //when
        Role role = Role.group(right1, right2);

        //then
        List<Right> rights = role.getRights();
        assertThat(rights.size(), is(2));
        assertThat(rights.get(0), is(right1));
        assertThat(rights.get(1), is(right2));
    }

    @Test(expected = RightTypeException.class)
    public void shouldNotGroupRightsOfDifferentTypes() throws Exception, RightTypeException {
        //given
        Right right1 = Right.ofType(RightType.ORDER_FULFILLMENT);
        Right right2 = Right.ofType(RightType.SUPERVISION);

        //when
        Role.group(right1, right2);
    }
}
