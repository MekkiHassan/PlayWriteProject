package pages;

import Utils.ConfigReader;
import Utils.Waits;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.testng.Assert;
import Utils.ElementActions;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    //variables
    private Page page;
    private final Locator Username;
    private final Locator Password;
    private final Locator LoginBTN;
    private final Locator toaster;
    private final Locator dropDownList;
    private final Locator validLicense;
//Constructor
    public LoginPage (Page page) {
        this.page=page;
        this.toaster=  page.getByRole(AriaRole.ALERT).filter(new Locator.FilterOptions().setHasText("User permissions not loaded."));
        this.Username= page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username"));
        this.Password=   page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
        this.dropDownList=  page.locator(".select__indicator");
        this.validLicense=   page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("AdvancedSolutions"));
        this.LoginBTN=  page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Log in"));

    }

 //Actions

  /*  public LoginPage clickStart(){
        ElementActions.CLICK(startBtn);
        return this;
    }*/
  @Step("Enter Username: {0}")
     public  LoginPage enterUsername(String username){

    ElementActions.sendData(Username,username);
         return this;
     }
    @Step("Enter Password: {1}")
    public  LoginPage enterPassword(String password){
        ElementActions.sendData(Password,password);
        return this;
    }
    @Step("Select the Company")
    public LoginPage selectLicense(){
        ElementActions.CLICK(dropDownList);
        ElementActions.CLICK(validLicense);

        return this;
    }
    @Step("Click on Login Button")
    public  void clickLoginBTN(){
        ElementActions.CLICK(LoginBTN);
    }

    public void checkFromNavigation(){
        String expectedUrl = ConfigReader.getProperty("path");
        page.waitForURL(expectedUrl);

        Assert.assertEquals(page.url(), expectedUrl);
    }

    public String isErrorMessageVisible() throws InterruptedException {
     toaster.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        if (toaster.isVisible()){
            return toaster.innerText();
     }
       return null;
    }




}

