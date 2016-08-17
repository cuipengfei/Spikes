package org.openlmis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.openlmis.RightType.GENERAL_ADMIN;
import static org.openlmis.RightType.ORDER_FULFILLMENT;

public class RoleAssignmentTest {

    private static class TestStub extends RoleAssignment {
        public TestStub(Role role) throws RightTypeException {
            super(role);
        }

        @Override
        protected List<RightType> getAcceptableRightTypes() {
            List<RightType> acceptableRightTypes = new ArrayList<>();
            acceptableRightTypes.add(GENERAL_ADMIN);
            return acceptableRightTypes;
        }

        @Override
        public boolean contains(Right right) {
            return false;
        }
    }

    @Test
    public void shouldAllowCreationWithMatchingRoleTypes() throws Exception, RightTypeException {
        new TestStub(Role.group(Right.ofType(GENERAL_ADMIN)));
    }

    @Test(expected = RightTypeException.class)
    public void shouldNotAllowCreationWithMismatchingRoleTypes() throws Exception, RightTypeException {
        new TestStub(Role.group(Right.ofType(ORDER_FULFILLMENT)));
    }
}