package cashregister;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConfigReaderTest {
    @Test
    public void shouldLoadProducts() throws Exception {
        URL resource = this.getClass().getClassLoader().getResource("config.properties");
        List<Product> products = ConfigReader.loadProducts(resource.getPath());

        assertThat(products.size(), is(3));

        assertThat(products.get(0).name(), is("可口可乐"));
        assertThat(products.get(1).unit(), is("个"));
        assertThat(products.get(2).singleUnitPrice(), is(5.5d));
        assertThat(products.get(2).code(), is("ITEM000003"));
    }
}