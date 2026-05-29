package com.victormtz.tests.ui;

import com.victormtz.base.BaseTest;
import com.victormtz.pages.LoginPage;
import com.victormtz.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(driver);
    }

    @Test(description = "Verificar que se muestran 6 productos en el inventario")
    public void testInventoryItemCount() {
        Assert.assertEquals(inventoryPage.getInventoryItemCount(), 6,
                "El inventario debe mostrar 6 productos");
    }

    @Test(description = "Ordenar productos de A a Z")
    public void testSortByNameAZ() {
        inventoryPage.sortBy("Name (A to Z)");
        List<String> names = inventoryPage.getItemNames();
        List<String> sorted = names.stream().sorted().collect(java.util.stream.Collectors.toList());
        Assert.assertEquals(names, sorted, "Los productos deben estar ordenados de A a Z");
    }

    @Test(description = "Ordenar productos de Z a A")
    public void testSortByNameZA() {
        inventoryPage.sortBy("Name (Z to A)");
        List<String> names = inventoryPage.getItemNames();
        List<String> sorted = names.stream()
                .sorted(java.util.Comparator.reverseOrder())
                .collect(java.util.stream.Collectors.toList());
        Assert.assertEquals(names, sorted, "Los productos deben estar ordenados de Z a A");
    }

    @Test(description = "Ordenar productos por precio de menor a mayor")
    public void testSortByPriceLowToHigh() {
        inventoryPage.sortBy("Price (low to high)");
        List<Double> prices = inventoryPage.getItemPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1),
                    "Los precios deben estar ordenados de menor a mayor");
        }
    }

    @Test(description = "Agregar producto al carrito desde inventario")
    public void testAddItemToCart() {
        inventoryPage.addItemToCartByIndex(0);
        Assert.assertEquals(inventoryPage.getCartCount(), 1,
                "El carrito debe mostrar 1 producto");
    }
}