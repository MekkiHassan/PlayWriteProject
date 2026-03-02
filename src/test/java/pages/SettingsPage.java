package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SettingsPage {
    private Page page;
    private SidebarComponent sidebar;
    private final Locator generalSetting;
    private final Locator hrSetting;
    private final Locator gender;
    private final Locator nationality;
    private final Locator employeeStatus;
    private final Locator  position ;
    private final Locator positionDegree;
    private final Locator salaryDisbursementMethod;
    private final Locator addNewNationalityBTN;
    private final Locator code;
    private final Locator englishNameField;
    private final Locator arabicNameField;
    private final Locator saveBTN;

    private final Locator editBTN;
    private final Locator deleteBTN1;
    private final Locator deleteBTN2;


    public SettingsPage(Page page) {
    this.page=page;
    this.sidebar = new SidebarComponent(page);
    generalSetting=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("General Setting"));
    hrSetting=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("HR"));
    gender=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Icon Gender"));
    nationality=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Icon Nationality"));
    employeeStatus=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Icon Employee Status"));
    position=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Icon Position").setExact(true));
    positionDegree=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Icon Position Degree"));
    salaryDisbursementMethod=page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Icon Salary Disbursement"));
    addNewNationalityBTN=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add New"));
    code=page.getByPlaceholder("Code");
    englishNameField=page.getByPlaceholder("English Name");
    arabicNameField=page.getByPlaceholder("Arabic Name");
    saveBTN=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
    editBTN=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Edit"));
    deleteBTN1=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete"));
    deleteBTN2=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes Delete"));


    }

    ///  Actions
    ///

    @Step ("Click on General settings")
    public SettingsPage clickGeneralSetting(){
        sidebar.openSidebar().openSettingPage();
        generalSetting.click();
        return this;
    }

    @Step ("Assert that the Gender & Nationality & Employee Status are Exist")
    public void assert_Gender_Nationality_employeeStatusIsAppears(){
        assertThat(gender).isVisible();
        assertThat(nationality).isVisible();
        assertThat(employeeStatus).isVisible();
    }

    @Step ("Click on HR settings")
    public SettingsPage selectHrSetting(){
        sidebar.openSidebar().openSettingPage();
        hrSetting.click();
        return this;
    }

    @Step ("Assert that the Position & Position Degree & Salary Disbursement Method are Exist")
    public void assert_Position_PositionDegree_SalaryDisbursementMethod_IsAppears(){
        assertThat(position).isVisible();
        assertThat(positionDegree).isVisible();
        assertThat(salaryDisbursementMethod).isVisible();
    }


    @Step (" Add new Nationality ")
    public SettingsPage addNewNationality(String Code, String englishName, String arabicName){
        nationality.click();
        addNewNationalityBTN.click();
        code.fill(Code);
        englishNameField.fill(englishName);
        arabicNameField.fill(arabicName);
        saveBTN.click();
        assertThat(page.getByText("Created Successfully")).isVisible();
        return this;
    }


    @Step (" Delete new Nationality")
    public void deleteNationality(String code, String enName, String arName) {
        // بنبني اللوكيتور وقت التنفيذ بناءً على البيانات اللي مبعوتة
        String rowName = code + " " + enName + " " + arName;

        page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(rowName))
                .getByRole(AriaRole.BUTTON).click(); // يفتح الـ 3 نقط بتوع الصف ده بالظبط

        deleteBTN1.click();
        deleteBTN2.click();

        assertThat(page.getByText("Deleted Successfully")).isVisible();
    }

}
