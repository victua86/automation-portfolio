package com.victormtz.tests.api;

import com.victormtz.api.base.BaseApiTest;
import com.victormtz.api.models.Order;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderTest extends BaseApiTest {

    private static final long ORDER_ID = 987654321L;
    private static final long PET_ID = 123456789L;

    @Test(priority = 1, description = "POST - Crear una nueva orden")
    public void testCreateOrder() {
        Order order = new Order(ORDER_ID, PET_ID, 1, "placed", false);

        given()
                .spec(requestSpec)
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200)
                .body("id", equalTo((int) ORDER_ID))
                .body("petId", equalTo((int) PET_ID))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(false));
    }

    @Test(priority = 2, description = "GET - Obtener orden por ID")
    public void testGetOrderById() {
        Order order = given()
                .spec(requestSpec)
                .when()
                .get("/store/order/" + ORDER_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(Order.class);

        Assert.assertEquals(order.getId(), ORDER_ID);
        Assert.assertEquals(order.getStatus(), "placed");
    }

    @Test(priority = 3, description = "GET - Inventario de la tienda")
    public void testGetStoreInventory() {
        given()
                .spec(requestSpec)
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200)
                .body("available", notNullValue());
    }

    @Test(priority = 4, description = "DELETE - Eliminar orden")
    public void testDeleteOrder() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/store/order/" + ORDER_ID)
                .then()
                .statusCode(200);
    }

    @Test(priority = 5, description = "GET - Orden eliminada retorna 404")
    public void testGetDeletedOrder() {
        given()
                .spec(requestSpec)
                .when()
                .get("/store/order/" + ORDER_ID)
                .then()
                .statusCode(404);
    }
}