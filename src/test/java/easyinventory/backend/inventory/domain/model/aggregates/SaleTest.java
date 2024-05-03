package easyinventory.backend.inventory.domain.model.aggregates;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class SaleTest {

    @Test
    public void testQuantityProductsInSale() {
        Product product1 = new Product( "Laptop", 10, 10, 10, 10, 10, 1L);
        Product product2 = new Product( "Laptop2", 10, 10, 10, 10, 10, 1L);
        Sale sale = new Sale("Leo","10-10-2021",200);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        sale.addProducts(products);
        Integer esperado = 2;
        assertEquals(sale.getProductsCount(), esperado);
    }

    @Test
    public void testTotalCost() {
        Product product1 = new Product( "Laptop", 10, 10, 10, 10, 10, 1L);
        Product product2 = new Product( "Laptop2", 10, 10, 10, 10, 10, 1L);
        Sale sale = new Sale("Leo","10-10-2021",200);

        List<Product> cart = new ArrayList<>();
        cart.add(product1);
        cart.add(product2);

        sale.addProducts(cart);
        Integer esperado = 20;
        assertEquals(sale.calculateTotalCost(), esperado);
        sale.removeProduct(product1);

        esperado = 10;
        assertEquals(sale.calculateTotalCost(), esperado);
    }

}