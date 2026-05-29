package com.victormtz.tests.api;

import com.victormtz.api.base.BaseApiTest;
import com.victormtz.api.models.Pet;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.anyOf;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTest extends BaseApiTest {

    private static final long PET_ID = 123456789L;

    @Test(priority = 1, description = "POST - Crear una nueva mascota")
    public void testCreatePet() {
        Pet pet = new Pet(PET_ID, "Firulais", "available");

        given()
                .spec(requestSpec)
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("id", equalTo((int) PET_ID))
                .body("name", equalTo("Firulais"))
                .body("status", equalTo("available"));
    }

    @Test(priority = 2, description = "GET - Obtener mascota por ID")
    public void testGetPetById() {
        Pet pet = given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + PET_ID)
                .then()
                .statusCode(200)
                .extract()
                .as(Pet.class);

        Assert.assertEquals(pet.getName(), "Firulais");
        Assert.assertEquals(pet.getStatus(), "available");
    }

    @Test(priority = 3, description = "PUT - Actualizar mascota")
    public void testUpdatePet() {
        Pet updatedPet = new Pet(PET_ID, "Firulais Updated", "sold");

        given()
                .spec(requestSpec)
                .body(updatedPet)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Firulais Updated"))
                .body("status", equalTo("sold"));
    }

    @Test(priority = 4, description = "DELETE - Eliminar mascota")
    public void testDeletePet() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/pet/" + PET_ID)
                .then()
                .statusCode(200);
    }

    @Test(priority = 5, description = "GET - Verificar que mascota fue eliminada")
    public void testGetDeletedPet() {
        given()
                .spec(requestSpec)
                .when()
                .get("/pet/" + PET_ID)
                .then()
                .statusCode(404);
    }
    @Test(priority = 6, description = "GET - Buscar mascotas por status available")
    public void testGetPetsByStatusAvailable() {
        given()
                .spec(requestSpec)
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("status", everyItem(equalTo("available")));
    }

    @Test(priority = 7, description = "GET - Buscar mascotas por status sold")
    public void testGetPetsByStatusSold() {
        given()
                .spec(requestSpec)
                .queryParam("status", "sold")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("status", everyItem(equalTo("sold")));
    }

    @Test(priority = 8, description = "GET - Status inválido retorna lista vacía o error")
    public void testGetPetsByStatusInvalid() {
        given()
                .spec(requestSpec)
                .queryParam("status", "invalidStatus")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(400)));
    }
}