package org.openlmis;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RightTest {
    @Test
    public void shouldHaveAttachedRightsOfSameType() throws Exception {
        //given
        Right right = Right.ofType(RightType.SUPERVISION);

        //when
        Right attachment = Right.ofType(RightType.SUPERVISION);
        right.attach(attachment);

        right.attach(Right.ofType(RightType.ORDER_FULFILLMENT));

        //then
        List<Right> attachedRights = right.getAttachments();
        assertThat(attachedRights.size(), is(1));
        assertThat(attachedRights.get(0), is(attachment));
    }
}
