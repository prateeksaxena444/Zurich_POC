package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage {
    private WebDriver driver;

    // Locators
    private By productList = By.className("inventory_list");
    private By errorMessage = By.cssSelector("h3[data-test='error']");
    private By addToCartButtons = By.cssSelector(".btn_primary");
    private By cartIcon = By.className("shopping_cart_link");
    private By cartItemCount = By.className("shopping_cart_badge");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductListVisible() {
        try {
            return driver.findElement(productList).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public void addFirstProductToCart() {
        WebElement firstProductButton = driver.findElement(addToCartButtons);
        firstProductButton.click();
    }

    public void addSecondProductToCart() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (buttons.size() > 1) {
            buttons.get(1).click(); // Click on the second product's add to cart button
        }
    }

    public boolean hasProductIssue(String productName) {
        // This method can check if a specific product has issues based on its name.
        // For now, let's return false as a placeholder.
        return false; // Placeholder
    }

    public boolean isProductAddedToCart() {
        // Logic to check if product is added to cart (could check cart count)
        driver.findElement(cartIcon).click();
        return !driver.findElements(cartItemCount).isEmpty();
    }
}
