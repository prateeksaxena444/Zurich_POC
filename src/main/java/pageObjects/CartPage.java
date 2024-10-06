package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    private WebDriver driver;

    // Locators
    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.id("checkout");
    private By checkoutPage = By.className("checkout_info");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCart() {
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();
    }

    public boolean isCartItemPresent() {
        List<WebElement> items = driver.findElements(cartItems);
        return !items.isEmpty();
    }

    public int getCartItemCount() {
        String count = driver.findElement(By.className("shopping_cart_badge")).getText();
        return Integer.parseInt(count);
    }

    public void proceedToCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public boolean isCheckoutPageVisible() {
        try {
            return driver.findElement(checkoutPage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
