package task.solve;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BasketClassTest {

    BasketClass BasketTest;
    @Before
    public void setUp() {
        this.BasketTest = new BasketClass(null, 0);
    }

    @After
    public void clear() {
        this.BasketTest = null;
    }

    @Test
    public void addProduct() {
        for(int i = 0; i < 10; i++) {
            this.BasketTest.addProduct(String.valueOf(i), i*i);
        }
        for(int i = 0; i < 10; i++) {
            assertThat(this.BasketTest.getProducts(), hasItems(String.valueOf(i)));
        }
        assertEquals("!{null : 0}", 0, this.BasketTest.getProductQuantity(null));
        this.BasketTest.addProduct(null, 1);
        assertEquals("!{null : 1}", 1, this.BasketTest.getProductQuantity(null));
    }

    @Test
    public void removeProduct() {
        for(int i = 0; i < 10; i++) {
            this.BasketTest.addProduct(String.valueOf(i), i*i);
        }
        this.BasketTest.removeProduct("1");
        assertThat(this.BasketTest.getProducts(), hasItems("1"));

        assertThat(this.BasketTest.getProducts(), hasItems("100"));
        this.BasketTest.removeProduct("100");
        assertThat(this.BasketTest.getProducts(), hasItems("100"));

        this.BasketTest.removeProduct(null);
        assertFalse("null is here", this.BasketTest.getProducts().contains(null));
        //        assertTrue("BasketTest is null", this.BasketTest == );
    }

    @Test
    public void updateProductQuantity() {
        this.BasketTest.updateProductQuantity(null, 1);
        assertEquals("!{null : 1}", 1, this.BasketTest.getProductQuantity(null));

        this.BasketTest.addProduct("0", 0);
        this.BasketTest.updateProductQuantity("0", 0);
        assertThat(this.BasketTest.getProducts(), hasItems("0"));

    }

    @Test
    public void getProducts() {
        this.BasketTest.removeProduct(null);
        assertTrue("products is null", this.BasketTest.getProducts() == null);
        this.BasketTest.addProduct("\n", 0);
        assertThat(this.BasketTest.getProducts(), hasItems("\n"));
    }

    @Test
    public void getProductQuantity() {
//        this.BasketTest.updateProductQuantity(null, 1);
        assertEquals("!{null : 0}", 0, this.BasketTest.getProductQuantity(null));
    }
}