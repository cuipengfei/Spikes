package cashregister;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InputParserTest {
    @Test
    public void shouldParseJsonInputToLineItems() throws Exception {
        List<OrderLineItem> orderLineItems = InputParser.parse("[" +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000001'," +
                " 'ITEM000002-2'," +
                " 'ITEM000003'," +
                " 'ITEM000003'," +
                " 'ITEM000003'" +
                "]");

        assertThat(orderLineItems.size(), is(3));
        assertThat(orderLineItems.get(0).amount(), is(5));
        assertThat(orderLineItems.get(1).amount(), is(2));
        assertThat(orderLineItems.get(2).amount(), is(3));
    }
}