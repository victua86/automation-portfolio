package com.victormtz.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "[data-test='firstName']")
    private WebElement firstNameInput;

    @FindBy(css = "[data-test='lastName']")
    private WebElement lastNameInput;

    @FindBy(css = "[data-test='postalCode']")
    private WebElement postalCodeInput;

    @FindBy(css = "[data-test='continue']")
    private WebElement continueButton;

    @FindBy(css = "[data-test='finish']")
    private WebElement finishButton;

    @FindBy(css = "[data-test='complete-header']")
    private WebElement confirmationHeader;

    @FindBy(css = "[data-test='complete-text']")
    private WebElement confirmationText;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = "[data-test='subtotal-label']")
    private WebElement subtotalLabel;

    @FindBy(css = "[data-test='tax-label']")
    private WebElement taxLabel;

    @FindBy(css = "[data-test='total-label']")
    private WebElement totalLabel;

    @FindBy(css = "[data-test='cancel']")
    private WebElement cancelButton;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.elementToBeClickable(firstNameInput));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);
    }

    public void fillShippingInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        finishButton.click();
    }

    public void clickCancel() {
        cancelButton.click();
    }

    public String getConfirmationHeader() {
        return confirmationHeader.getText();
    }

    public String getConfirmationText() {
        return confirmationText.getText();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public String getSubtotal() {
        return subtotalLabel.getText();
    }

    public String getTotal() {
        return totalLabel.getText();
    }

    public boolean isOrderConfirmed() {
        return driver.getCurrentUrl().contains("checkout-complete");
    }

    public boolean isOnCheckoutStepOne() {
        return driver.getCurrentUrl().contains("checkout-step-one");
    }

    public boolean isOnCheckoutStepTwo() {
        return driver.getCurrentUrl().contains("checkout-step-two");
    }

    public boolean isErrorDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}