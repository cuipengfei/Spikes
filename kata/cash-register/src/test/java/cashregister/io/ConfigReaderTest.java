package cashregister.io;

import cashregister.models.Product;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class ConfigReaderTest {
    @Test
    public void shouldLoadProducts() throws Exception {
        URL resource = this.getClass().getClassLoader().getResource("config.properties");
        List<Product> products = ConfigReader.loadProducts(resource.getPath());

        MatcherAssert.assertThat(products.size(), is(3));

        MatcherAssert.assertThat(products.get(0).name(), is("可口可乐"));
        MatcherAssert.assertThat(products.get(0).appliedDiscounts().size(), is(1));

        MatcherAssert.assertThat(products.get(1).unit(), is("个"));
        MatcherAssert.assertThat(products.get(1).appliedDiscounts().size(), is(0));

        MatcherAssert.assertThat(products.get(2).singleUnitPrice(), is(5.5d));
        MatcherAssert.assertThat(products.get(2).appliedDiscounts().size(), is(2));
        MatcherAssert.assertThat(products.get(2).appliedDiscounts().get(0).priority(), is(100));
        MatcherAssert.assertThat(products.get(2).appliedDiscounts().get(1).priority(), is(200));
    }
}