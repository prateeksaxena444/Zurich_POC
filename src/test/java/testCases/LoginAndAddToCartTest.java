package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.CartPage;
import pageObjects.LoginPage;
import pageObjects.ProductsPage;
import utilities.TestUtil;

public class LoginAndAddToCartTest extends BaseTest {

    @DataProvider(name = "getLoginData")
    public Object[][] getLoginData() {
        return TestUtil.getTestData("LoginData");
    }

    @Test(dataProvider = "getLoginData")
    public void loginAndVerify(String username, String password, String expectedOutcome) {
        test = extent.createTest("Login Test for user: " + username);

        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        test.log(com.aventstack.extentreports.Status.INFO, "Logging in to SauceDemo with user: " + username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (username.equals("standard_user")) {
            Assert.assertTrue(productsPage.isProductListVisible(), "Product list should be visible for logged-in user.");
            test.log(com.aventstack.extentreports.Status.INFO, "Login successful for user: " + username);

            test.log(com.aventstack.extentreports.Status.INFO, "Adding product to cart");
            productsPage.addFirstProductToCart();
            Assert.assertTrue(productsPage.isProductAddedToCart(), "Product was not added to the cart");

            test.log(com.aventstack.extentreports.Status.PASS, "Product added to cart successfully for user: " + username);
        } else if (username.equals("locked_out_user")) {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed for user: " + username);
            Assert.assertEquals(loginPage.getErrorMessage(), expectedOutcome, "Error message should match the expected message.");
            test.log(com.aventstack.extentreports.Status.PASS, "Login correctly failed for user: " + username);
        } else if (username.equals("problem_user")) {
            Assert.assertTrue(productsPage.isProductListVisible(), "Product list should be visible for problem user.");
            test.log(com.aventstack.extentreports.Status.INFO, "Login successful for problem user: " + username);
            Assert.assertFalse(productsPage.hasProductIssue("Some Problematic Product"), "Some products should have issues with problem user.");
            test.log(com.aventstack.extentreports.Status.PASS, "Login successful for problem user with issues verified.");
        } else if (username.equals("performance_glitch_user")) {
            Assert.assertTrue(productsPage.isProductListVisible(), "Product list should be visible for performance glitch user.");
            test.log(com.aventstack.extentreports.Status.INFO, "Login successful for performance glitch user: " + username);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.log(com.aventstack.extentreports.Status.INFO, "Adding product to cart with performance glitch");
            productsPage.addFirstProductToCart();
            Assert.assertTrue(productsPage.isProductAddedToCart(), "Product was not added to the cart");
            test.log(com.aventstack.extentreports.Status.PASS, "Product added to cart successfully for performance glitch user.");
        }
    }

    // MOre tests for standard_user
    private final String username = "standard_user";
    private final String password = "secret_sauce";

    @Test(priority = 1)
    public void successfulLogin() {
        test = extent.createTest("Successful Login Test for standard_user");
        LoginPage loginPage = new LoginPage(driver);

        test.log(com.aventstack.extentreports.Status.INFO, "Logging in with user: " + username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertTrue(productsPage.isProductListVisible(), "Product list should be visible after login.");
        test.log(com.aventstack.extentreports.Status.PASS, "Login successful for user: " + username);
    }

    @Test(priority = 2)
    public void addSingleProductToCart() {
        test = extent.createTest("Add Single Product to Cart Test for standard_user");

        successfulLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        test.log(com.aventstack.extentreports.Status.INFO, "Adding the first product to cart.");
        productsPage.addFirstProductToCart();

        Assert.assertTrue(productsPage.isProductAddedToCart(), "Product should be added to the cart.");
        test.log(com.aventstack.extentreports.Status.PASS, "Single product added to cart successfully.");
    }

    @Test(priority = 3)
    public void addMultipleProductsToCart() {
        test = extent.createTest("Add Multiple Products to Cart Test for standard_user");

        successfulLogin();

        ProductsPage productsPage = new ProductsPage(driver);
        test.log(com.aventstack.extentreports.Status.INFO, "Adding multiple products to cart.");

        productsPage.addFirstProductToCart();
        productsPage.addSecondProductToCart();

        Assert.assertTrue(productsPage.isProductAddedToCart(), "Both products should be added to the cart.");
        test.log(com.aventstack.extentreports.Status.PASS, "Multiple products added to cart successfully.");
    }

    @Test(priority = 4)
    public void viewCartContents() {
        test = extent.createTest("View Cart Contents Test for standard_user");

        addMultipleProductsToCart(); // Ensure products are added

        CartPage cartPage = new CartPage(driver);
        test.log(com.aventstack.extentreports.Status.INFO, "Opening cart to verify contents.");
        cartPage.openCart();

        Assert.assertTrue(cartPage.isCartItemPresent(), "Cart should contain items.");
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart item count should be 2.");
        test.log(com.aventstack.extentreports.Status.PASS, "Cart contents verified successfully.");
    }

    @Test(priority = 5)
    public void checkoutProcess() {
        test = extent.createTest("Checkout Process Test for standard_user");

        viewCartContents(); // Ensure have items in the cart

        CartPage cartPage = new CartPage(driver);
        test.log(com.aventstack.extentreports.Status.INFO, "Proceeding to checkout.");
        cartPage.proceedToCheckout();

        Assert.assertTrue(cartPage.isCheckoutPageVisible(), "Checkout page should be visible.");
        test.log(com.aventstack.extentreports.Status.PASS, "Checkout process verified successfully.");
    }
}
