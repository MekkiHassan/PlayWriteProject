package tests;

import Utils.ConfigReader;
import base.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SettingsPage;

public class SettingsTest extends BaseTest {

    @Test (priority = 1)
    public void openGeneralSettingPage()
    {
        new LoginPage(page)
                .enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .selectLicense()
                .clickLoginBTN();

        new SettingsPage(page)
                .clickGeneralSetting()
                .assert_Gender_Nationality_employeeStatusIsAppears();
    }



    @Test
    public void add_New_Nationality(){
        new LoginPage(page)
                .enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .selectLicense()
                .clickLoginBTN();
        new SettingsPage(page)
                .clickGeneralSetting()
                .addNewNationality(
                        ConfigReader.getProperty("NationalityCode"),
                        ConfigReader.getProperty("NationalityEnglishName"),
                        ConfigReader.getProperty("NationalityArabicName")
                );
    }
    @Test
    public void delete_Nationality(){
        new LoginPage(page)
                .enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .selectLicense()
                .clickLoginBTN();

        new SettingsPage(page)
                .clickGeneralSetting()
                .addNewNationality(ConfigReader.getProperty("NationalityCode"),
                        ConfigReader.getProperty("NationalityEnglishName"),
                        ConfigReader.getProperty("NationalityArabicName"))

                .deleteNationality(
                        ConfigReader.getProperty("NationalityCode"),
                        ConfigReader.getProperty("NationalityEnglishName"),
                        ConfigReader.getProperty("NationalityArabicName")
                );
    }


  /*  @Test  (priority = 2)
    public void openHRSettingPage()
    {
        new LoginPage(page)
                .enterUsername(ConfigReader.getProperty("username"))
                .enterPassword(ConfigReader.getProperty("password"))
                .selectLicense()
                .clickLoginBTN();

        new SettingsPage(page)
                .selectHrSetting()
                .assert_Position_PositionDegree_SalaryDisbursementMethod_IsAppears();
    }*/

}
