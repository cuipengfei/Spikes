package org.openlmis;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SanityTest {
    @org.junit.Test
    public void sanity() throws Exception {
        assertThat(1, is(1));
    }
}
