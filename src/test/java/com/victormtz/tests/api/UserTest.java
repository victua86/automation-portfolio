package com.victormtz.tests.api;

import com.victormtz.api.base.BaseApiTest;
import com.victormtz.api.models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserTest extends BaseApiTest {

    private static final String USERNAME = "victormtz_test";

    @Test(priority = 1, description = "POST - Crear un nuevo usuario")
    public void testCreateUser() {
        User user = new User(1L, USERNAME, "Victor", "Martinez",
                "victor@test.com", "password123", "1234567890", 1);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/user")
                .then()
                .statusCode(200);
    }

    @Test(priority = 2, description = "GET - Obtener usuario por username")
    public void testGetUserByUsername() {
        User user = given()
                .spec(requestSpec)
                .when()
                .get("/user/" + USERNAME)
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);

        Assert.assertEquals(user.getUsername(), USERNAME);
        Assert.assertEquals(user.getFirstName(), "Victor");
        Assert.assertEquals(user.getEmail(), "victor@test.com");
    }

    @Test(priority = 3, description = "PUT - Actualizar usuario")
    public void testUpdateUser() {
        User updatedUser = new User(1L, USERNAME, "Victor Updated", "Martinez",
                "victor_updated@test.com", "newpassword", "9876543210", 1);

        given()
                .spec(requestSpec)
                .body(updatedUser)
                .when()
                .put("/user/" + USERNAME)
                .then()
                .statusCode(200);
    }

    @Test(priority = 4, description = "GET - Login de usuario")
    public void testLoginUser() {
        given()
                .spec(requestSpec)
                .queryParam("username", USERNAME)
                .queryParam("password", "password123")
                .when()
                .get("/user/login")
                .then()
                .statusCode(200)
                .body(containsString("logged in"));
    }

    @Test(priority = 5, description = "GET - Logout de usuario")
    public void testLogoutUser() {
        given()
                .spec(requestSpec)
                .when()
                .get("/user/logout")
                .then()
                .statusCode(200);
    }

    @Test(priority = 6, description = "DELETE - Eliminar usuario")
    public void testDeleteUser() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/user/" + USERNAME)
                .then()
                .statusCode(200);
    }

    @Test(priority = 7, description = "GET - Usuario eliminado retorna 404")
    public void testGetDeletedUser() {
        given()
                .spec(requestSpec)
                .when()
                .get("/user/" + USERNAME)
                .then()
                .statusCode(404);
    }
}