package com.victormtz.tests.ui;

import com.victormtz.base.BaseTest;
import com.victormtz.pages.CartPage;
import com.victormtz.pages.CheckoutPage;
import com.victormtz.pages.InventoryPage;
import com.victormtz.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        inventoryPage.addItemToCartByIndex(0);
        cartPage.goToCart();
        cartPage.clickCheckout();
    }

    @Test(description = "Verificar que el checkout step 1 carga correctamente")
    public void testCheckoutStepOneLoads() {
        Assert.assertTrue(checkoutPage.isOnCheckoutStepOne(),
                "Debe estar en el paso 1 del checkout");
    }

    @Test(description = "Flujo completo de compra exitosa")
    public void testCompleteCheckout() {
        checkoutPage.fillShippingInfo("Victor", "Martinez", "37000");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnCheckoutStepTwo(),
                "Debe avanzar al paso 2 del checkout");

        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
                "La orden debe confirmarse correctamente");
        Assert.assertEquals(checkoutPage.getConfirmationHeader(),
                "Thank you for your order!");
    }

    @Test(description = "Checkout sin ingresar datos muestra error")
    public void testCheckoutWithoutInfo() {
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Debe mostrar un error si no se llenan los campos");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name"),
                "El error debe mencionar el campo First Name");
    }

    @Test(description = "Checkout sin apellido muestra error")
    public void testCheckoutWithoutLastName() {
        checkoutPage.enterFirstName("Victor");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorDisplayed(),
                "Debe mostrar error si falta el apellido");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Last Name"),
                "El error debe mencionar el campo Last Name");
    }

    @Test(description = "Verificar que el resumen muestra subtotal y total")
    public void testOrderSummaryDisplayed() {
        checkoutPage.fillShippingInfo("Victor", "Martinez", "37000");
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnCheckoutStepTwo(),
                "Debe estar en el paso 2");
        Assert.assertTrue(checkoutPage.getSubtotal().contains("Item total"),
                "Debe mostrar el subtotal");
        Assert.assertTrue(checkoutPage.getTotal().contains("Total"),
                "Debe mostrar el total");
    }

    @Test(description = "Cancelar checkout regresa al carrito")
    public void testCancelCheckout() {
        checkoutPage.clickCancel();

        Assert.assertTrue(cartPage.isOnCartPage(),
                "Cancelar debe regresar al carrito");
    }
}