package com.victormtz.tests.ui;

import com.victormtz.base.BaseTest;
import com.victormtz.data.TestDataProvider;
import com.victormtz.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Map;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = TestDataProvider.class,
            description = "Validar login con múltiples usuarios de Sauce Demo")
    public void testLogin(Map<String, String> userData) {
        String username    = userData.get("username");
        String password    = userData.get("password");
        String expected    = userData.get("expectedResult");
        String description = userData.get("description");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expected.equals("success")) {
            Assert.assertTrue(
                    driver.getCurrentUrl().contains("inventory"),
                    "[FAIL] " + description + " debería iniciar sesión correctamente"
            );
        } else {
            Assert.assertTrue(
                    loginPage.isErrorDisplayed(),
                    "[FAIL] " + description + " debería mostrar error"
            );
        }
    }
}