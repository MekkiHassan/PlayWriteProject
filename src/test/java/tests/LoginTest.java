package tests;

import Utils.Waits;
import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import Utils.ConfigReader;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {
    @Epic("Login Module")
    @Feature("Login Scenarios")
    @Story("As a user, I want to login with different credentials")
    @Severity(SeverityLevel.BLOCKER)
    @Description("تست شامل لعملية تسجيل الدخول بأكثر من حالة (Valid & Invalid)")

    @Test(dataProvider = "loginTestData")
    // 1. تعريف الداتا (الخزنة اللي فيها الحالات المختلفة)
    @DataProvider(name = "loginTestData")
    public Object[][] loginData() {
        return new Object[][] {

                { "wrong_user", "123456", "invalidFields" },           // يوزر غلط
                { "Youssri001", "wrong_pass", "invalidPass" }, // باسوورد غلط
                { "Youssri", "Youssri@12345", "valid" }   // حالة صحيحة

        };
    }
    // 2. ربط التست بالـ DataProvider
    @Test(dataProvider = "loginTestData")
    public void LoginWithMultipleScenarios(String user, String pass, String type) throws Exception {

            LoginPage loginPage = new LoginPage(page);
            loginPage
                    .enterUsername(user)
                    .enterPassword(pass)
                    .selectLicense()
                    .clickLoginBTN();

         switch (type){
             case "valid":
                 loginPage.checkFromNavigation();
                 break;
             case "invalidFields":
                String actualMessage= loginPage.isErrorMessageVisible();
                    Assert.assertEquals(actualMessage, "User permissions not loaded.");
                 break;
             case "invalidPass":
                 loginPage.isErrorMessageVisible();
                 String actualMessage1= loginPage.isErrorMessageVisible();
                 Assert.assertEquals(actualMessage1, "User permissions not loaded..");

                 break;
             default:
                 throw new IllegalArgumentException("Invalid test type provided: " + type);
         }

    }
}


