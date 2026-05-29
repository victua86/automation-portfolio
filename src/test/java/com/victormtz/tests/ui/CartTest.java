package com.victormtz.tests.ui;

import com.victormtz.base.BaseTest;
import com.victormtz.pages.CartPage;
import com.victormtz.pages.InventoryPage;
import com.victormtz.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test(description = "Agregar un producto al carrito y verificar")
    public void testAddOneItemToCart() {
        inventoryPage.addItemToCartByIndex(0);
        cartPage.goToCart();

        Assert.assertTrue(cartPage.isOnCartPage(), "Debe estar en la página del carrito");
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "El carrito debe tener 1 producto");
    }

    @Test(description = "Agregar múltiples productos al carrito")
    public void testAddMultipleItemsToCart() {
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        inventoryPage.addItemToCartByIndex(2);
        cartPage.goToCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 3, "El carrito debe tener 3 productos");
    }

    @Test(description = "Eliminar un producto del carrito")
    public void testRemoveItemFromCart() {
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        cartPage.goToCart();

        Assert.assertEquals(cartPage.getCartItemCount(), 2, "El carrito debe tener 2 productos");

        cartPage.removeItemByIndex(0);

        Assert.assertEquals(cartPage.getCartItemCount(), 1, "El carrito debe tener 1 producto después de eliminar");
    }

    @Test(description = "Vaciar el carrito completamente")
    public void testEmptyCart() {
        inventoryPage.addItemToCartByIndex(0);
        inventoryPage.addItemToCartByIndex(1);
        cartPage.goToCart();

        cartPage.removeItemByIndex(0);
        cartPage.removeItemByIndex(0);

        Assert.assertTrue(cartPage.isCartEmpty(), "El carrito debe estar vacío");
    }

    @Test(description = "Continuar comprando desde el carrito regresa al inventario")
    public void testContinueShopping() {
        inventoryPage.addItemToCartByIndex(0);
        cartPage.goToCart();
        cartPage.clickContinueShopping();

        Assert.assertTrue(inventoryPage.isOnInventoryPage(),
                "Debe regresar a la página de inventario");
    }
}