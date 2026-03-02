package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class SidebarComponent {
    private Page page;
    private final   Locator sidBarArrow;
    private final  Locator settingModule;
    private final Locator organizationModule;
    private final Locator attendanceManagement;

    public SidebarComponent(Page page) {
        this.page=page;
        sidBarArrow= page.locator(".w-\\[26px\\]").first();
        settingModule=page.locator("div").filter(new Locator.FilterOptions().setHasText("Settings")).nth(3);
        organizationModule= page.locator("div").filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Organization$"))).first();
        attendanceManagement=page.locator("div").filter(new Locator.FilterOptions().setHasText("Attendance Management")).nth(3);
    }

   // Actions
   public SidebarComponent openSidebar(){
       sidBarArrow.click();
return  this;
   }

    public SidebarComponent openSettingPage(){
            settingModule.click();
        return  this;
    }
    public void organizationPage(){
        organizationModule.click();
    }
    public void attendanceManagementPage(){
        attendanceManagement.click();
    }


}
